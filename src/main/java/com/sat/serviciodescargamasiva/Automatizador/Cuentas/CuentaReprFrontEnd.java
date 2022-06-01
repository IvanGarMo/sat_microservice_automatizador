package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.Data;

@Data
public class CuentaReprFrontEnd {
    private long idCuenta;
    private String codigoPadre;
    private String codigo;
    private long idCategoria;
    private int nivel;
    private String descripcion;
    private int idUsuario;
    private int idCliente;
    private boolean esCuentaOrigen;
    private boolean esCuentaPersonalizada;
    private boolean cuentaNivelUsuario;
    private boolean cuentaNivelCliente;
}
