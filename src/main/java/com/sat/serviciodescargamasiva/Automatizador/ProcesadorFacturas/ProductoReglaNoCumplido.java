package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoReglaNoCumplido {
    private long claveProdServ;
    private long idSolicitud;
    private boolean activo;
    private long idCliente;
    private long idUsuario;
}
