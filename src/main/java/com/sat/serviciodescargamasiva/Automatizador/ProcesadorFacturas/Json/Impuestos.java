package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Impuestos {
    @JsonProperty("Traslados")
    private Traslado[] traslados;
    @JsonProperty("Retenciones")
    private Retencion[] retenciones;
}
