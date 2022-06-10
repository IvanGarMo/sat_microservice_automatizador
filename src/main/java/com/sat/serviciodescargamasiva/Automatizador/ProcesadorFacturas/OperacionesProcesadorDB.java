package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.FacturaDespliegue;

import java.util.List;

public interface OperacionesProcesadorDB {
    List<Regla> cargaReglas(long idCliente) throws JsonProcessingException;
    List<TipoImpuesto> cargaImpuesto() throws JsonProcessingException;
    List<RetencionRegla> cargaRetenciones() throws JsonProcessingException;
    void guardaClaveProdPendienteRegla(long idSolicitud, ProductosReglaNoCumplidoJson productos) throws JsonProcessingException;
    void guardaFacturaProcesada(long idDescarga, Factura factura) throws JsonProcessingException;

}
