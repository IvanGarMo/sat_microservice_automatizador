package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FacturaPublica {
    private String idFactura;
    private List<Cuenta> cuenta;
    private boolean emisor;
    private boolean receptor;

    public FacturaPublica(Factura factura) {
        this.idFactura = factura.getIdFactura();
        this.cuenta = factura.getCuentas();
        if(factura.getClienteEmisorReceptor() == EmisorReceptor.EMISOR) {
            this.emisor = true;
            this.receptor = false;
        } else {
            this.emisor = false;
            this.receptor = true;
        }
    }

}
