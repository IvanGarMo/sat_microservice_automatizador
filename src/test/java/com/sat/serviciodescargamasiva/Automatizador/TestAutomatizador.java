package com.sat.serviciodescargamasiva.Automatizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.OperacionesProcesadorDB;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.ProductoReglaNoCumplido;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.ProductosReglaNoCumplidoJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestAutomatizador {
    @Autowired
    private OperacionesProcesadorDB procesadorRepo;

    @Test
    public void test() throws JsonProcessingException {
        ProductosReglaNoCumplidoJson prodsJson = new ProductosReglaNoCumplidoJson();
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101500, 8, true)
        );
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101501, 8, true)
        );
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101502, 8, true)
        );
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101504, 8, true)
        );
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101505, 8, true)
        );
        prodsJson.addProducto(
                new ProductoReglaNoCumplido(10101506, 8, true)
        );
        procesadorRepo.guardaClaveProdPendienteRegla(8, prodsJson);
    }
}
