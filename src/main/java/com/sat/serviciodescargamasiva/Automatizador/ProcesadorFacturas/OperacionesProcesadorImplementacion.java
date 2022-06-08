package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OperacionesProcesadorImplementacion implements OperacionesProcesadorDB {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Regla> cargaReglas(long idCliente) throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Regla_Usuario_Carga");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCliente", idCliente);

        Map<String, Object> outParamMap = jdbc.execute(inParamMap);
        Object objJson = outParamMap.get("_resultado");

        if(objJson == null) return new ArrayList<Regla>();

        String strJson = objJson.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        Regla[] reglas = objectMapper.readValue(strJson, Regla[].class);
        return Arrays.asList(reglas);
    }

    @Override
    public TipoImpuesto[] cargaImpuesto() throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Impuesto_Carga");
        Map<String, Object> inParamMap = new HashMap<>();

        Map<String, Object> outParamMap = jdbc.execute(inParamMap);
        String json = outParamMap.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        TipoImpuesto[] impuestos = objectMapper.readValue(json, TipoImpuesto[].class);
        return impuestos;
    }

    @Override
    public void guardaClaveProdPendienteRegla(long idSolicitud, ProductosReglaNoCumplidoJson productos) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        String json = objMapper.writeValueAsString(productos);
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Guarda_Pendientes");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idSolicitud", idSolicitud);
        inParamMap.put("_productos", json);
        jdbc.execute(inParamMap);
    }
}
