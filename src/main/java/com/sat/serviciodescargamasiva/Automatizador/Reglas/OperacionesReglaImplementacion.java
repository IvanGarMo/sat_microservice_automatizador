package com.sat.serviciodescargamasiva.Automatizador.Reglas;

import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class OperacionesReglaImplementacion implements OperacionesRegla {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall jdbc;

    @Override
    public ResponseData creaReglaNivelUsuario(String uidUserFirebase, long idCuenta, long claveProdServ) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CreaReglaNivelUsuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_uidUserFirebase", uidUserFirebase);
        inParamMap.put("_idCuenta", idCuenta);
        inParamMap.put("_claveProdServ", claveProdServ);

        return obtenerRespuesta(jdbc, inParamMap);
    }

    @Override
    public ResponseData creaReglaNivelCliente(long idCliente, long idCuenta, long claveProdServ) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CreaReglaNivelCliente");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCliente", idCliente);
        inParamMap.put("_idCuenta", idCuenta);
        inParamMap.put("_claveProdServ", claveProdServ);

        return obtenerRespuesta(jdbc, inParamMap);
    }

    @Override
    public ResponseData eliminaReglaNivelUsuario(long idRegla) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("EliminaReglaNivelUsuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idRegla", idRegla);

        return obtenerRespuesta(jdbc, inParamMap);
    }

    @Override
    public ResponseData eliminaReglaNivelCliente(long idRegla) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("EliminaReglaNivelCliente");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idRegla", idRegla);

        return obtenerRespuesta(jdbc, inParamMap);
    }

    private ResponseData obtenerRespuesta(SimpleJdbcCall jdbcCall, Map<String, Object> inParamMap) {
        Map<String, Object> outParamMap = jdbcCall.execute(inParamMap);
        ResponseData rd = new ResponseData();
        boolean opValida = ((boolean) outParamMap.get("_opvalida"));
        log.info("OpValida: "+opValida);
        String mensaje = outParamMap.get("_mensaje").toString();
        log.info("Mensaje: "+mensaje);
        rd.setOpValida(opValida);
        rd.addMensaje(mensaje);
        return  rd;
    }
}
