package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallesImplementacion {
    private boolean solicitudProcesadaCorrectamente;
    private boolean solicitudConAspectosPendientes;
    private boolean pendientes;
    private int conteoPendientes;
    private String descripcionEstado;
    private boolean solicitudAunNoProcesada;
}
