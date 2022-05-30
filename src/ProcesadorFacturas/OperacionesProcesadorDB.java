package com.sat.serviciodescargamasiva.automatizador.automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;

interface OperacionesProcesadorDB {
    Regla[] cargaReglas(long idCliente, String uidUserFirebase) throws JsonProcessingException;
    TipoImpuesto[] cargaImpuesto() throws JsonProcessingException;
}
