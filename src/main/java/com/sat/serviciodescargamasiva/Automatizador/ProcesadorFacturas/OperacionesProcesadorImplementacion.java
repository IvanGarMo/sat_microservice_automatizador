package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class OperacionesProcesadorImplementacion implements OperacionesProcesadorDB {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Regla[] cargaReglas(long idCliente, String uidUserFirebase) throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CargaReglasUsuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCliente", idCliente);
        inParamMap.put("_idUsuario", -1);

        Map<String, Object> outParamMap = jdbc.execute(inParamMap);
        String json = outParamMap.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        Regla[] reglas = objectMapper.readValue(json, Regla[].class);
        return reglas;
    }

    @Override
    public TipoImpuesto[] cargaImpuesto() throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CargaImpuesto");
        Map<String, Object> inParamMap = new HashMap<>();

        Map<String, Object> outParamMap = jdbc.execute(inParamMap);
        String json = outParamMap.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        TipoImpuesto[] impuestos = objectMapper.readValue(json, TipoImpuesto[].class);
        return impuestos;
    }

    @Override
    public void guardaClaveProdPendienteRegla(long claveProdServ, long idSolicitud) {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Guarda_Producto_Pendiente");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_claveProdServ", claveProdServ);
        inParamMap.put("_idSolicitud", idSolicitud);
        jdbc.execute(inParamMap);
    }
}
