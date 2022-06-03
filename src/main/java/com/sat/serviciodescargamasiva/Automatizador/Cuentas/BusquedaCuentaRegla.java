package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusquedaCuentaRegla {
    private long idCuenta;
    private long idUsuario;
    private long idCliente;
}
