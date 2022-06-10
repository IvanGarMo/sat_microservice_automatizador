package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDespliegue {
    private long idFactura;
    private String nombreFactura;
}
