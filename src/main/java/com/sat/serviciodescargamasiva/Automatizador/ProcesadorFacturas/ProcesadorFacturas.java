package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json.Concepto;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json.FacturaJson;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json.Retencion;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json.Traslado;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Service
public class ProcesadorFacturas {
    //private List<Factura> facturasProcesadas;
    private List<Factura> facturasPUE;
    private List<Factura> facturasNoPUE;
    private List<Regla> reglas;
    private List<TipoImpuesto> tipoImpuestos;
    private List<RetencionRegla> retenciones;
    @Autowired
    private OperacionesProcesadorDB facturasRepo;
    private ProductosReglaNoCumplidoJson jsonProductosSinRegla;
    private boolean productosPendientes;
    private long idSolicitud;
    private long idUsuario;
    private long idCliente;
    private String rfcCliente;

    public ProcesadorFacturas() {
        //this.facturasProcesadas = new ArrayList<>();
        this.facturasPUE = new ArrayList<>();
        this.facturasNoPUE = new ArrayList<>();
        this.jsonProductosSinRegla = new ProductosReglaNoCumplidoJson();
        this.productosPendientes = false;
    }

    public void initialize(long idSolicitud, long idCliente, String rfcCliente, long idUsuario) throws JsonProcessingException {
        this.idSolicitud = idSolicitud;
        this.idCliente = idCliente;
        this.rfcCliente = rfcCliente.toUpperCase();
        this.idUsuario = idUsuario;
        cargaReglas(idCliente);
    }

    private void cargaReglas(long idCliente) throws JsonProcessingException {
        this.reglas = facturasRepo.cargaReglas(idCliente);
        this.tipoImpuestos = facturasRepo.cargaImpuesto();
        this.cargaRetenciones();
        Collections.sort(this.reglas);
    }

    private void cargaRetenciones() throws JsonProcessingException {
        this.retenciones = facturasRepo.cargaRetenciones();
    }

    public boolean hayProductosPendientes() {
        return this.productosPendientes;
    }

    public void procesaFacturas(List<File> facturasAProcesar)
            throws ParserConfigurationException,
            IOException, SAXException,
            FacturaPueNotFoundException {
        //Seteamos si se usará debe o haber
        for(File f : facturasAProcesar) {
            //Obtenemos una representación en JSON de la factura
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            FacturaJson facturaJson = xmlMapper.readValue(f, FacturaJson.class);

            //También creamos un objeto factura local que manejará las cuentas
            Factura facturaEnTrabajo = new Factura();
            facturaEnTrabajo.setIdFactura(f.getName());

            Factura facturaProcesada = procesaFacturaIndividual(facturaEnTrabajo, facturaJson);
            if(facturaProcesada.isEsPUE()) {
                this.facturasPUE.add(facturaProcesada);
            } else {
                this.facturasNoPUE.add(facturaProcesada);
            }
        }

        //En el caso extremo de que ninguna factura haya sido PUE, se lo notificamos al usuario
        if(facturasPUE.isEmpty()) {
            throw new FacturaPueNotFoundException();
        }
    }

    private Factura procesaFacturaIndividual(Factura factura, FacturaJson facturaJson) throws IOException {
        //Primero es necesario indicar si el cliente es el emisor o el receptor de la factura
        if(facturaJson.getEmisor().equals(this.rfcCliente)) {
            factura.setClienteEmisorReceptor(EmisorReceptor.EMISOR);
        }

        //Solo trabajaremos con facturas PUE. Aquellas que tengan otro método de pago o no tengan el
        //campo incluido serán ignoradas
        if(facturaJson.getMetodoPago().equals(FacturaJson.PAGO_EN_UNA_SOLA_EXHIBICION)) {
            factura.setEsPUE(true);
        } else {
            factura.setEsPUE(false);
            return factura;
        }

        for(Concepto concepto : facturaJson.getConceptos()) {
            long claveProdServ;
            double importe;

            claveProdServ = Long.parseLong(concepto.getClaveProdServ());
            importe = Double.parseDouble(concepto.getImporte());
            String descripcionOperacion = concepto.getDescripcion();

            //Por cada concepto se creará una cuenta
            Cuenta cuenta = new Cuenta(factura.getClienteEmisorReceptor());
            cuenta.setImporte(importe);
            cuenta.setDescripcionOperacion(descripcionOperacion);

            Regla reglaAEncontrar = new Regla(claveProdServ);
            int indiceRegla = Collections.binarySearch(this.reglas, reglaAEncontrar);
            if(indiceRegla >= 0) {
                Regla reglaAplicable = this.reglas.get(indiceRegla);
                System.out.println("Regla: "+reglaAplicable);
                String codigo = reglaAplicable.getCodigoCuenta();
                cuenta.setCodigoCuenta(codigo);
                factura.addCuenta(cuenta);
            } else {
                System.out.println("Salvando producto Regla no cumplido");
                System.out.println("IdCliente: "+this.idCliente);
                System.out.println("IdUsuario: "+this.idUsuario);
                ProductoReglaNoCumplido prod = new ProductoReglaNoCumplido(
                        claveProdServ, this.idSolicitud, true, this.idCliente, this.idUsuario
                );
                this.jsonProductosSinRegla.addProducto(prod);
                this.productosPendientes = true;
            }

            //Luego tramitamos los impuestos de cada concepto
            //Luego sigue calcular los impuestos
            //Cuando en el receptor viene el cliente, el impuesto es acreditable y va en el debe
            //Cuando en el emisor viene el cliente, el impuesto es trasladado y va en el haber
            //Aquí uso el arreglo de impuestos, no el de reglas
            for(Traslado traslado : concepto.getImpuestos().getTraslados()) {
                //Checo por vacio porque hay ocasiones qne se exentan los impuestos,
                //sin embargo esta etiqueta todavia viene, por lo que avienta un error de puntero nulo
                if(traslado.getImporte() == null || traslado.getImporte().isEmpty()) continue;
                TipoImpuesto tipoImpuesto =
                        this.tipoImpuestos.stream().filter(
                                t -> t.getImpuesto().equals(traslado.getImpuesto())
                        ).findFirst().get();
                Cuenta cuentaImpuestoDeclarado;
                if(factura.getClienteEmisorReceptor() == EmisorReceptor.EMISOR) {
                    cuentaImpuestoDeclarado = new Cuenta(true, false);
                    cuentaImpuestoDeclarado.setDescripcionOperacion(tipoImpuesto.getDescripcionImpuesto());
                    cuentaImpuestoDeclarado.setImporte(Double.valueOf(traslado.getImporte()));
                } else {
                    cuentaImpuestoDeclarado = new Cuenta(false, true);
                    cuentaImpuestoDeclarado.setDescripcionOperacion(tipoImpuesto.getDescripcionImpuesto());
                    cuentaImpuestoDeclarado.setImporte(Double.valueOf(traslado.getImporte()));
                }
                factura.addCuenta(cuentaImpuestoDeclarado);
            }

            //Luego las retenciones, en caso de que alla
            Retencion[] retenciones = concepto.getImpuestos().getRetenciones();
            if(retenciones != null) {
                for(Retencion r : retenciones) {
                    Cuenta cuentaRetencion;
                    //Si la factura esta como emitida, la retención va del lado del debe
                    //Si la factura esta como recibida, va en el lado del haber
                    if(factura.getClienteEmisorReceptor() == EmisorReceptor.EMISOR) {
                        cuentaRetencion = new Cuenta(EmisorReceptor.RECEPTOR);
                        cuentaRetencion.setDebe(true);
                        cuentaRetencion.setHaber(false);
                    } else {
                        cuentaRetencion = new Cuenta(EmisorReceptor.EMISOR);
                        cuentaRetencion.setImporte(Double.valueOf(r.getImporte()));
                        cuentaRetencion.setDebe(false);
                        cuentaRetencion.setHaber(true);
                    }
                    cuentaRetencion.setImporte(Double.valueOf(r.getImporte()));
                    RetencionRegla retRegla =
                            this.retenciones.stream().filter(
                                    retCiclo
                                            -> retCiclo.getTasaCuota().equals(r.getTasaOCuota())).findFirst().get();
                    cuentaRetencion.setDescripcionOperacion(retRegla.getDescripcion());
                    factura.addCuenta(cuentaRetencion);
                }
            }
        }


        return factura;
    }

    private Cuenta procesarIvaRetenido(Node node, Factura factura, List<Regla> reglasRetencion) {
        Cuenta cuenta = new Cuenta(factura.getClienteEmisorReceptor());
        //Si el cliente es el emisor, el IVA retenido va en el deber
        if(factura.getClienteEmisorReceptor() == EmisorReceptor.EMISOR) {
            cuenta.setDebe(true);
            cuenta.setHaber(false);
        } else { //Si el cliente es el receptor, el IVA retenido va en el haber
            cuenta.setDebe(false);
            cuenta.setHaber(true);
        }

        //Con base en la tasa, encuentro el código que corresponse
        double tasa = (Double.parseDouble(node.getAttributes().getNamedItem("TasaOCuota").getTextContent())*100);
        Regla reglaAplicable = reglasRetencion.stream().filter(r -> r.getTasaRetenido() == tasa).findFirst().get();
        cuenta.setCodigoCuenta(reglaAplicable.getCodigoCuenta());

        cuenta.setImporte(Double.parseDouble(node.getAttributes().getNamedItem("Importe").getTextContent()));
        return cuenta;
    }

    public void guardaProductosPendientes() throws JsonProcessingException {
        this.facturasRepo.guardaClaveProdPendienteRegla(this.idSolicitud, this.jsonProductosSinRegla);
    }

}
