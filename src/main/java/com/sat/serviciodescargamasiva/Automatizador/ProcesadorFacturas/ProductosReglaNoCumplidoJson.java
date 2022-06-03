package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductosReglaNoCumplidoJson {
    @JsonProperty("ProductosReglaNoCumplidoJson")
    public List<ProductoReglaNoCumplido> productosSinRegla;

    public ProductosReglaNoCumplidoJson(List<ProductoReglaNoCumplido> productos) {
        this.productosSinRegla = productos;
    }

    public ProductosReglaNoCumplidoJson() {
        this.productosSinRegla = new ArrayList<>();
    }

    public void addProducto(ProductoReglaNoCumplido prod) {
        this.productosSinRegla.add(prod);
    }
}
