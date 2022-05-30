package com.sat.serviciodescargamasiva.Automatizador.Controllers;

import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.Reglas.OperacionesRegla;
import com.sat.serviciodescargamasiva.Automatizador.Reglas.Regla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reglas")
public class ReglasController {
    @Autowired
    private OperacionesRegla reglaRepo;

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
        ResponseData rd = reglaRepo.creaReglaNivelUsuario(uuid, regla.getIdCuenta(), regla.getClaveProdServ());
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
}
