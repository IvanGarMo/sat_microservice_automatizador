package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Retencion {
    @JsonProperty("Base")
    private String base;
    @JsonProperty("Importe")
    private String importe;
    @JsonProperty("Impuesto")
    private String impuesto;
    @JsonProperty("TasaOCuota")
    private String tasaOCuota;
    @JsonProperty("TipoFactor")
    private String tipoFactor;
}
