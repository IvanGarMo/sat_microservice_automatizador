package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OperacionesProcesadorDB {
    Regla[] cargaReglas(long idCliente, String uidUserFirebase) throws JsonProcessingException;
    TipoImpuesto[] cargaImpuesto() throws JsonProcessingException;
    void guardaClaveProdPendienteRegla(long idSolicitud, ProductosReglaNoCumplidoJson productos) throws JsonProcessingException;
}
