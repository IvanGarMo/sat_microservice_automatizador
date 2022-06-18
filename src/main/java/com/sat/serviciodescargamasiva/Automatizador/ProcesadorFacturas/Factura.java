package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Factura {
    private String idFactura;
    private List<Cuenta> cuentas;
    private EmisorReceptor clienteEmisorReceptor;
    private String rfc;

    private boolean esPUE;
    public Factura() {
        this.cuentas = new ArrayList<>();
    }

    public void addCuenta(Cuenta cuenta) {
        if(cuenta == null) {
            throw new NullPointerException();
        }

        this.cuentas.add(cuenta);
    }
}
