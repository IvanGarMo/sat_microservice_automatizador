package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = { "Rfc", "Nombre", "UsoCFDI"})
public class Receptor {
    @JsonProperty("Rfc")
    private String rfc;
    @JsonProperty("Nombre")
    private String nombre;
    @JsonProperty("UsoCFDI")
    private String usoCFDI;
}