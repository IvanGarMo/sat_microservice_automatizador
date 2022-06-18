package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReglaCuentaSimplificado {
    private long idRegla;
    private long claveProdServ;
    private String descripcionRegla;
    private String codigo;
    private String descripcionCuenta;
}
