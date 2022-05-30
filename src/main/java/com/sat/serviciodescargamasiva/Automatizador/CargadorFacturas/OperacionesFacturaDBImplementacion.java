package com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class OperacionesFacturaDBImplementacion implements OperacionesFacturaDB {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ZipFactura[] cargaZipFacturas(long idDescarga) throws JsonProcessingException,
            PaquetesNotFoundException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Carga_Paquete");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idDescarga", idDescarga);

        Map<String, Object> outParamMap = jdbc.execute(inParamMap);

        if(outParamMap.get("_resultado") == null) {
            throw new PaquetesNotFoundException();
        }

        String json = outParamMap.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ZipFactura[] zips = objectMapper.readValue(json, ZipFactura[].class);

        return zips;
    }
}
