package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Concepto {
    @JsonProperty("Cantidad")
    private String cantidad;
    @JsonProperty("ClaveProdServ")
    private String claveProdServ;
    @JsonProperty("ClaveUnidad")
    private String claveUnidad;
    @JsonProperty("Descripcion")
    private String descripcion;
    @JsonProperty("ValorUnitario")
    private String valorUnitario;
    @JsonProperty("Importe")
    private String importe;
    @JsonProperty("Unidad")
    private String unidad;
    private Impuestos Impuestos;

}
