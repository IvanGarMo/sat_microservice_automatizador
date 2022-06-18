package com.sat.serviciodescargamasiva.Automatizador.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.*;
import com.sat.serviciodescargamasiva.Automatizador.Reglas.OperacionesRegla;
import com.sat.serviciodescargamasiva.Automatizador.Reglas.Regla;
import com.sat.serviciodescargamasiva.Automatizador.permisos.Autorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reglas")
public class ReglasController {
    @Autowired
    private OperacionesRegla reglaRepo;
    @Autowired
    private Autorizacion autorizacion;
    @Autowired
    private OperacionesCuenta cuentaRepo;

    @PostMapping("/cliente")
    public ResponseEntity<ResponseData> creaReglaIndividual(@RequestHeader("uuid") String uuid,
                                                            @RequestBody Regla regla) {
        ResponseData rd = reglaRepo.creaReglaNivelCliente(regla.getIdCliente(), regla.getIdCuenta(),
                regla.getClaveProdServ());
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @PostMapping("/usuario")
    public ResponseEntity<ResponseData> creaReglaUsuario(@RequestHeader("uuid") String uuid,
                                                         @RequestBody Regla regla) {
        long idUsuario = autorizacion.cargaIdUsaurio(uuid);
        ResponseData rd = reglaRepo.creaReglaNivelUsuario(idUsuario, regla.getIdCuenta(), regla.getClaveProdServ());
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @DeleteMapping("/cliente/{idRegla}")
    public ResponseEntity<ResponseData> eliminaReglaIndividual(@RequestHeader("uuid") String uuid,
                                                               @PathVariable("idRegla") Long idRegla) {
        ResponseData rd = reglaRepo.eliminaReglaNivelCliente(idRegla);
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @DeleteMapping("/usuario/{idRegla}")
    public ResponseEntity<ResponseData> eliminaReglaUsuario(@PathVariable("idRegla") Long idRegla) {
        ResponseData rd = reglaRepo.eliminaReglaNivelUsuario(idRegla);
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @PostMapping("/cuenta")
    public ResponseEntity<CuentaRegla[]> creaCuentaRegla(@RequestBody BusquedaCuentaRegla busquedaCuentaRegla)
            throws JsonProcessingException {
        CuentaRegla[] cuentas = cuentaRepo.cargaReglasCuenta(busquedaCuentaRegla);
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/pendiente/{idSolicitud}")
    public ResponseEntity<ReglaSimplificado[]> cargaReglaSimplificado(@PathVariable("idSolicitud") long idSolicitud) throws JsonProcessingException {
        System.out.println("IdSolicitud: "+idSolicitud);
        ReglaSimplificado[] reglas = cuentaRepo.cargaPendientes(idSolicitud);
        System.out.println("ReglasPendiente: "+reglas);
        return new ResponseEntity<>(reglas, HttpStatus.OK);
    }

    @GetMapping("/reglas-por-cuenta/")
    public ResponseEntity<ReglaCuentaSimplificado[]> cargaReglaCuentaSimplificado(
                @RequestParam("idCliente") long idCliente,
                @RequestParam("idCuenta") long idCuenta) throws JsonProcessingException {
        ReglaCuentaSimplificado[] reglas = cuentaRepo.cargaReglaCuentaSimplificado(idCliente, idCuenta);
        return new ResponseEntity<>(reglas, HttpStatus.OK);
    }
}
