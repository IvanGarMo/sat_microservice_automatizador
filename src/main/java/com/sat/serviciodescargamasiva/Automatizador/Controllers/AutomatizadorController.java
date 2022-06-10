package com.sat.serviciodescargamasiva.Automatizador.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.Automatizador;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.ClienteSimplificado;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.DetallesImplementacion;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.FacturaDespliegue;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.OperacionesCuenta;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Factura;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.FacturaPublica;
import com.sat.serviciodescargamasiva.Automatizador.permisos.Autorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/automatizador")
public class AutomatizadorController {
    @Autowired
    private Automatizador automatizador;
    @Autowired
    private OperacionesCuenta cuentaRepo;
    @Autowired
    private Autorizacion autorizacion;

    @PostMapping("/{idDescarga}")
    public ResponseEntity<ResponseData> realizaContabilidad(@RequestHeader("uuid") String uidUserFirebase,
                                                            @PathVariable("idDescarga") long idDescarga) {
        long idUsuario = autorizacion.cargaIdUsaurio(uidUserFirebase);
        ClienteSimplificado datosCliente = cuentaRepo.cargaClienteSimplificado(idDescarga);
        automatizador.contabilizaFacturas(idDescarga, datosCliente.getRfc(), datosCliente.getIdCliente(), idUsuario);
        return new ResponseEntity<>(new ResponseData(), HttpStatus.OK);
    }

    @GetMapping("/detalles/{idDescarga}")
    public ResponseEntity<DetallesImplementacion> cargaDetalles(@RequestHeader("uuid") String uidUserFirebase,
                                                                @PathVariable("idDescarga") long idDescarga) {
        DetallesImplementacion detalles = cuentaRepo.cargaDetallesContabilidad(idDescarga);
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/lista-facturas/{idDescarga}")
    public ResponseEntity<List<FacturaDespliegue>> cargaFacturas(@RequestHeader("uuid") String uidUserFirebase,
                                                                 @PathVariable("idDescarga") long idDescarga) throws JsonProcessingException {
        List<FacturaDespliegue> facturas = cuentaRepo.cargaFacturasDespliegue(idDescarga);
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @GetMapping("/detalles-factura/{idFactura}")
    public ResponseEntity<FacturaPublica> cargaFacturaPublica(@RequestHeader("uuid") String uidUserFirebase,
                                                                    @PathVariable("idfactura") long idFactura)
                                                                throws JsonProcessingException {
        FacturaPublica facturaPublica = cuentaRepo.cargaFactura(idFactura);
        return new ResponseEntity<>(facturaPublica, HttpStatus.OK);
    }
}
