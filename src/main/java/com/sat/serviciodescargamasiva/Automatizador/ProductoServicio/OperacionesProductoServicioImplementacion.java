package com.sat.serviciodescargamasiva.Automatizador.ProductoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OperacionesProductoServicioImplementacion implements OperacionesProductoServicio {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall jdbc;

    @Override
    public ProductoServicio encuentraProductoServicio(long claveProdServ) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Carga_Detalles_Producto_Servicio");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_claveProdServ", claveProdServ);
        try {
            Map<String, Object> outParamMap = new HashMap<>();
            long claveProdServSalida = ((long) outParamMap.get("_claveProdServ"));
            String descripcion = outParamMap.get("_descripcion").toString();
            ProductoServicio productoServicio = new ProductoServicio(claveProdServSalida, descripcion);
            return productoServicio;
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
