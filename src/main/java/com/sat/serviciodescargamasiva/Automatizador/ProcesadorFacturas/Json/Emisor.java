package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Emisor {
    @JsonProperty("Nombre")
    private String nombre;
    @JsonProperty("RegimenFiscal")
    private String regimenFiscal;
    @JsonProperty("Rfc")
    private String rfc;
}