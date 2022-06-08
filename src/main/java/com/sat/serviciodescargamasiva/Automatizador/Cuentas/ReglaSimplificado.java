package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.Data;

@Data
public class ReglaSimplificado {
    private long id;
    private long claveProdServ;
    private String descripcion;
    private boolean activo;
    private long idCliente;
}
