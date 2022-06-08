package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.Data;

@Data
public class ClienteSimplificado {
    private long idCliente;
    private String rfc;
    private String nombre;
}
