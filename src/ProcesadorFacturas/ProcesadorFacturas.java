package com.sat.serviciodescargamasiva.automatizador.automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private OperacionesProcesadorDB facturasRepo;

    public ProcesadorFacturas() {
        //this.facturasProcesadas = new ArrayList<>();
        this.facturasPUE = new ArrayList<>();
        this.facturasNoPUE = new ArrayList<>();
    }

    public void procesaFacturas(List<File> facturasAProcesar, String rfcCliente, long idCliente, String uidUser)
            throws ParserConfigurationException,
            IOException, SAXException,
            FacturaPueNotFoundException {

        //Cargamos las reglas
        cargaReglas(idCliente, uidUser);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        //Seteamos si se usará debe o haber
        for(File f : facturasAProcesar) {
            Factura facturaEnTrabajo = new Factura();
            facturaEnTrabajo.setIdFactura(f.getName());

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            doc.getDocumentElement().normalize();
            Factura facturaProcesada = procesaFacturaIndividual(rfcCliente, facturaEnTrabajo, doc);
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

    private void cargaReglas(long idCliente, String uidUser) throws JsonProcessingException {
        System.out.println("Voy a cargar las reglas");
        this.reglas = Arrays.asList(facturasRepo.cargaReglas(idCliente, uidUser));
        this.tipoImpuestos = Arrays.asList(facturasRepo.cargaImpuesto());
        Collections.sort(this.reglas);
    }

    private Factura procesaFacturaIndividual(String rfcCliente, Factura factura, Document document) {
        //Primero es necesario indicar si el cliente es el emisor o el receptor de la factura
        rfcCliente = rfcCliente.toUpperCase();
        Node nodeComprobante = document.getElementsByTagName("cfdi:Comprobante").item(0);
        Node metodoPagoAttribute = nodeComprobante.getAttributes().getNamedItem("MetodoPago");

        //Solo trabajaremos con facturas PUE. Aquellas que tengan otro método de pago o no tengan el
        //campo incluido serán ignoradas
        if(metodoPagoAttribute == null || !metodoPagoAttribute.getTextContent().equalsIgnoreCase("PUE")) {
            factura.setEsPUE(false);
            return factura;
        } else {
            factura.setEsPUE(true);
        }

        NodeList nodeListEmisor = document.getElementsByTagName("cfdi:Emisor");
        NodeList nodeListReceptor = document.getElementsByTagName("cfdi:Receptor");
        String rfcEmisor = nodeListEmisor.item(0).getAttributes().getNamedItem("Rfc").getTextContent();
        String rfcReceptor = nodeListReceptor.item(0).getAttributes().getNamedItem("Rfc").getTextContent();

        if(rfcEmisor.equals(rfcCliente)) {
            factura.setClienteEmisorReceptor(EmisorReceptor.EMISOR);
        } else if(rfcReceptor.equals(rfcCliente)) {
            factura.setClienteEmisorReceptor(EmisorReceptor.RECEPTOR);
        }

        NodeList nodeList = document.getElementsByTagName("cfdi:Concepto");
        if(nodeList == null) {
            return null;
        }

        //Por cada concepto que venga en la factura, será necesario hacer una cuenta
        for(int i=0; i<nodeList.getLength(); i++) {
            Cuenta cuenta = new Cuenta(factura.getClienteEmisorReceptor());
            Node node = nodeList.item(i);

            //Cargamos los datos de cada operación registrada en la factura y los guardamos
            long claveProdServ = Long.parseLong(node.getAttributes().getNamedItem("ClaveProdServ").getTextContent());
            //System.out.println("Buscando claveProdServ: "+claveProdServ);
            double importe = Double.parseDouble(node.getAttributes().getNamedItem("Importe").getTextContent());
            String descripcionOperacion = node.getAttributes().getNamedItem("Descripcion").getTextContent();
            cuenta.setImporte(importe);
            cuenta.setDescripcionOperacion(descripcionOperacion);

            //Encontramos la regla que aplique para este caso
            Regla reglaAEncontrar = new Regla(claveProdServ);

            int indiceRegla = Collections.binarySearch(this.reglas, reglaAEncontrar);

            if(indiceRegla >= 0) {
                Regla reglaAplicable = this.reglas.get(indiceRegla);
                String codigo = reglaAplicable.getCodigoCuenta();
                cuenta.setCodigoCuenta(codigo);
                factura.addCuenta(cuenta);
            }
        }

        //Luego sigue calcular los impuestos
        //Cuando en el receptor viene el cliente, el impuesto es acreditable y va en el debe
        //Cuando en el emisor viene el cliente, el impuesto es trasladado y va en el haber

        /*NodeList impuestos = document.getElementsByTagName("cfdi:Traslados");

        Regla reglaIvaAcreditable = this.reglas.stream().filter(regla -> regla.isImpuestoAcreditable()).findFirst().get();
        Regla reglaIvaTrasladado = this.reglas.stream().filter(regla -> regla.isImpuestoTrasladado()).findFirst().get();

        List<Regla> reglasRetencion = new ArrayList<>();
        this.reglas.stream().forEach(regla -> {
            if(regla.isImpuestoTrasladado()) reglasRetencion.add(regla);
        });

        Cuenta impuesto;
        for(int i=0; i<impuestos.getLength(); i++) {
            impuesto = new Cuenta(factura.getClienteEmisorReceptor());
            impuesto.setImporte(Double.parseDouble(impuestos.item(i).getAttributes().getNamedItem("Importe").getTextContent()));
            if(factura.getClienteEmisorReceptor() == EmisorReceptor.EMISOR) {
                impuesto.setCodigoCuenta(reglaIvaAcreditable.getCodigoCuenta());
            } else {
                impuesto.setCodigoCuenta(reglaIvaTrasladado.getCodigoCuenta());
            }
        }*/

        //Busco por impuestos retenidos. Puede no haber

        /*Cuenta impuestoRetenido;
        NodeList retenciones = document.getElementsByTagName("cfdi:Retenciones");
        if(retenciones != null) {
            NodeList listaRetenciones = document.getElementsByTagName("cfdi:Retencion");
            for(int i=0; i<listaRetenciones.getLength(); i++) {
                Node retencionActual = listaRetenciones.item(0);
                String tipoImpuesto = retencionActual.getAttributes().getNamedItem("Impuesto").getTextContent();

                //Requiere distinto procesamiento, dependiendo si es IVA o impuesto retenido
                if(TipoImpuesto.esRetencionIva(tipoImpuesto)) {
                    impuestoRetenido = procesarIvaRetenido(retencionActual, factura, reglasRetencion);
                    factura.addCuenta(impuestoRetenido);
                } else if(TipoImpuesto.esRetencionISR(tipoImpuesto)) {

                }

            }
        }*/

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

}
