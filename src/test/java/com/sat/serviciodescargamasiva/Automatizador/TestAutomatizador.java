package com.sat.serviciodescargamasiva.Automatizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.Automatizador;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.OperacionesCuenta;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Cuenta;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Factura;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.FacturaPueNotFoundException;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.ProcesadorFacturas;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class TestAutomatizador {
    @Autowired
    private OperacionesCuenta cuentaRepo;
    @Autowired
    private Automatizador automatizador;
    @Autowired
    private ProcesadorFacturas procesadorFacturas;

    @Test
    public void testLectura() throws IOException, FacturaPueNotFoundException, ParserConfigurationException, SAXException {
        String pathFolder = "/home/ivanovich/Desktop/XML DR DOMINGO";
        File[] files = new File(pathFolder).listFiles();
        List<File> listFiles = Arrays.asList(files);
        procesadorFacturas.initialize(8, 200, "MONE711201Q44", 1);
        procesadorFacturas.procesaFacturas(listFiles);
        List<Factura> facturaPue = procesadorFacturas.getFacturasPUE();
        facturaPue.forEach(f -> {
            System.out.println("Procesando la factura: "+f);
            List<Cuenta> cuentas = f.getCuentas();
            System.out.println("-----------------------------------------");
            cuentas.forEach(c -> {
                System.out.println(c);
            });
        });
    }
}
