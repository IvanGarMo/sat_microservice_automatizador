package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface OperacionesProcesadorDB {
    List<Regla> cargaReglas(long idCliente) throws JsonProcessingException;
    TipoImpuesto[] cargaImpuesto() throws JsonProcessingException;
    void guardaClaveProdPendienteRegla(long idSolicitud, ProductosReglaNoCumplidoJson productos) throws JsonProcessingException;
}
