package com.sat.serviciodescargamasiva.Automatizador;

import com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas.CargadorFacturas;
import com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas.PaquetesNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class DescargadorTest {
    @Autowired
    private CargadorFacturas cargadorFacturas;

    @Test
    public void descargaFacturas() throws PaquetesNotFoundException, IOException {
        List<File> facturas = cargadorFacturas.obtenFacturas(8);
        for(File f : facturas) {
            System.out.println(f.getName());
        }
    }
}
