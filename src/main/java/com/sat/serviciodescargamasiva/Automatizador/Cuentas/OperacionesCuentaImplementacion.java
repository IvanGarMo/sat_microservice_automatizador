package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OperacionesCuentaImplementacion implements OperacionesCuenta {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CategoriaCuentaJpa categoriaCuentaRepo;
    private SimpleJdbcCall jdbc;

    @Override
    public ResponseData creaCuenta(Cuenta cuenta) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Crea");
        Map<String, Object> inParamMap = new HashMap<>();
        System.out.println("CodigoPadre: "+cuenta.getCodigoPadre());
        inParamMap.put("_codigoPadre", cuenta.getCodigoPadre());
        System.out.println("Codigo: "+cuenta.getCodigo());
        inParamMap.put("_codigo", cuenta.getCodigo());
        System.out.println("IdCategoria: "+cuenta.getIdCategoria());
        inParamMap.put("_idCategoria", cuenta.getIdCategoria());
        inParamMap.put("_nivel", cuenta.getNivel());
        inParamMap.put("_descripcion", cuenta.getDescripcion());
        inParamMap.put("_idUsuario", cuenta.getIdUsuario());
        inParamMap.put("_idCliente", cuenta.getIdCliente());
        inParamMap.put("_cuentaNivelUsuario", cuenta.isCuentaNivelUsuario());
        inParamMap.put("_cuentaNivelCliente", cuenta.isCuentaNivelCliente());

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        ResponseData rd = new ResponseData();

        boolean opValida = ((boolean) outParam.get("_opValida"));
        String mensaje = outParam.get("_mensaje").toString();
        rd.setOpValida(opValida);
        rd.addMensaje(mensaje);
        return  rd;
    }

    @Override
    public ResponseData eliminaCuenta(long idCuenta) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Elimina");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCuenta", idCuenta);

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        ResponseData rd = new ResponseData();

        boolean opValida = ((boolean) outParam.get("_opValida"));
        String mensaje = outParam.get("_mensaje").toString();
        rd.setOpValida(opValida);
        rd.addMensaje(mensaje);
        return  rd;
    }

    @Override
    public Cuenta[] cargaCuentas(long idUsuario, long idCliente) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Carga");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idUsuario", idUsuario);
        inParamMap.put("_idCliente", idCliente);

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        ResponseData rd = new ResponseData();
        String resultadoJson = outParam.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        Cuenta[] cuentas = objectMapper.readValue(resultadoJson, Cuenta[].class);
        return cuentas;
    }

    @Override
    public ClienteInfoBasica[] cargaClientes(long idUsuario) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Carga_Cliente");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idUsuario", idUsuario);

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        ResponseData rd = new ResponseData();
        String resultadoJson = outParam.get("_resultado").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        ClienteInfoBasica[] clientes = objectMapper.readValue(resultadoJson, ClienteInfoBasica[].class);
        return clientes;
    }

    @Override
    public ResponseData actualizaNombreCuenta(long idCuenta, String descripcion) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Cambia_Nombre");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCuenta", idCuenta);
        inParamMap.put("_descripcion", descripcion);

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        ResponseData rd = new ResponseData();

        boolean opValida = ((boolean) outParam.get("_opValida"));
        String mensaje = outParam.get("_mensaje").toString();
        rd.setOpValida(opValida);
        rd.addMensaje(mensaje);
        return  rd;
    }

    @Override
    public String cargaCuentasParaDespliegue(String uidUserFirebase) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("CargaCuentasParaDespliegue");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_uidUserFirebase", uidUserFirebase);

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        return outParam.get("_resultado").toString();
    }

    @Override
    public Iterable<CategoriaCuenta> cargaCategoriasCuentas() {
        return categoriaCuentaRepo.findAll();
    }

    @Override
    public CuentaRegla[] cargaReglasCuenta(BusquedaCuentaRegla busqCuentaRegla) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Carga_Regla");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCuenta", busqCuentaRegla.getIdCuenta());
        inParamMap.put("_idUsuario", busqCuentaRegla.getIdUsuario());
        inParamMap.put("_idCliente", busqCuentaRegla.getIdCliente());

        Map<String, Object> outParam = jdbc.execute(inParamMap);
        Object outResultado = outParam.get("_resultado");
        if(outResultado == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResultado = outResultado.toString();
        CuentaRegla[] cuentaReglas = objectMapper.readValue(jsonResultado, CuentaRegla[].class);
        return cuentaReglas;
    }
}
