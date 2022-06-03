package com.sat.serviciodescargamasiva.Automatizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.ClienteInfoBasica;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.OperacionesCuenta;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestNombre {
    @Autowired
    private OperacionesCuenta cuentaRepo;

    @Test
    public void pruebaLectura() throws JsonProcessingException {
        long idUsuario = 1;
        long idCliente = 1;
        for(int i=0; i<200; i++) {
            cuentaRepo.cargaCuentas(idUsuario, idCliente);
        }
    }
}
