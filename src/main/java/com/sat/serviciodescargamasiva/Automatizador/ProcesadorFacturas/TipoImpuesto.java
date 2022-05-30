package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import lombok.Data;

@Data
public class TipoImpuesto {

    private final static String ISR = "001";
    private final static String IVA = "002";
    private String impuesto;
    private String siglasImpuesto;
    private String descripcionImpuesto;

    public static boolean esRetencionIva(String texto) {
        return texto.equals(IVA);
    }

    public static boolean esRetencionISR(String texto) {
        return texto.equals(ISR);
    }
}
