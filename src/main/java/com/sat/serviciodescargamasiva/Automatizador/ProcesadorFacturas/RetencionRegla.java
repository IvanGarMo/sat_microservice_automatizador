package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import lombok.Data;

@Data
public class RetencionRegla {
    private String impuesto;
    private String abreviacion;
    private String descripcion;
    private String tipoFactor;
    private String tasaCuota;
}
