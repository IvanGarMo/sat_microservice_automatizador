package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;

interface OperacionesProcesadorDB {
    Regla[] cargaReglas(long idCliente, String uidUserFirebase) throws JsonProcessingException;
    TipoImpuesto[] cargaImpuesto() throws JsonProcessingException;
    void guardaClaveProdPendienteRegla(long claveProdServ, long idSolicitud);
}
