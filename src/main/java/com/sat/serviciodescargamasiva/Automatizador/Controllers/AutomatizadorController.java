package com.sat.serviciodescargamasiva.Automatizador.Controllers;

import com.sat.serviciodescargamasiva.Automatizador.Automatizador.Automatizador;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.ClienteSimplificado;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.OperacionesCuenta;
import com.sat.serviciodescargamasiva.Automatizador.permisos.Autorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
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
}
