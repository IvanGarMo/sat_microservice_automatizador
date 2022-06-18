package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Factura;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.FacturaPublica;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        System.out.println("ProcedimientoAlmacenado: "+cuenta);
        Map<String, Object> inParamMap = new HashMap<>();
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

    @Override
    public ReglaSimplificado[] cargaPendientes(long idDescarga) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Carga_Pendientes");
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("_idSolicitud", idDescarga);

        Map<String, Object> outParam = jdbc.execute(inParam);
        Object objResultado = outParam.get("_resultado");
        if(objResultado == null) return null;

        String jsonResultado = objResultado.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        ReglaSimplificado[] reglas = objectMapper.readValue(jsonResultado, ReglaSimplificado[].class);
        return reglas;
    }

    @Override
    public Cuenta[] cuentasPorCategoriaCliente(long idCategoria, long idUsuario, long idCliente) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Carga_Por_Categoria");
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("_idCategoria", idCategoria);
        inParam.put("_idUsuario", idUsuario);
        inParam.put("_idCliente", idCliente);

        Map<String, Object> outParam = jdbc.execute(inParam);
        Object objResultado = outParam.get("_resultado");
        if(objResultado == null) return null;

        String jsonResultado = objResultado.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Cuenta[] cuentas = objectMapper.readValue(jsonResultado, Cuenta[].class);
        return cuentas;
    }

    @Override
    public ClienteSimplificado cargaClienteSimplificado(long idSolicitud) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cliente_Carga_Simplificado");
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("_idSolicitud", idSolicitud);

        Map<String, Object> outParam = jdbc.execute(inParam);
        String rfcCliente = outParam.get("_rfcCliente").toString();
        long idCliente = Long.valueOf(outParam.get("_idCliente").toString());
        String nombre = outParam.get("_nombreCliente").toString();
        ClienteSimplificado cliente = new ClienteSimplificado();
        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombre);
        cliente.setRfc(rfcCliente);
        return cliente;
    }

    @Override
    public DetallesImplementacion cargaDetallesContabilidad(long idSolicitud) {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Carga_Detalles_Procesamiento");
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("_idSolicitud", idSolicitud);

        Map<String, Object> outParam = jdbc.execute(inParam);
        boolean solicitudProcesadaCorrectamente = ((boolean) outParam.get("_solicitudProcesadaCorrectamente"));
        boolean solicitudProcesadaConAspectosPendientes = ((boolean) outParam.get("_solicitudConAspectosPendientes"));
        boolean pendientes = ((boolean) outParam.get("_pendientes"));
        int conteoPendientes = ((int) outParam.get("_conteoPendientes"));
        String descripcionEstado = outParam.get("_descripcionEstado").toString();
        boolean solicitudAunNoProcesada = ((boolean) outParam.get("_solicitudAunNoProcesada"));

        DetallesImplementacion detallesImplementacion =
                new DetallesImplementacion(solicitudProcesadaCorrectamente, solicitudProcesadaConAspectosPendientes,
                        pendientes, conteoPendientes, descripcionEstado, solicitudAunNoProcesada);
        return detallesImplementacion;
    }

    @Override
    public List<FacturaDespliegue> cargaFacturasDespliegue(long idSolicitud) throws JsonProcessingException {
        jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Carga_Facturas_Solicitud");
        Map<String, Object> inParam = new HashMap<>();
        inParam.put("_idDescarga", idSolicitud);

        Map<String, Object> outParam = new HashMap<>();
        Object jsonObj = outParam.get("_facturaSimplificado");
        if(jsonObj == null) return null;

        String jsonString = jsonObj.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        FacturaDespliegue[] facturaDespliegue = objectMapper.readValue(jsonString, FacturaDespliegue[].class);
        return Arrays.asList(facturaDespliegue);
    }

    @Override
    public FacturaPublica cargaFactura(long idFactura) throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Automatizador_Carga_Factura");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idRegistro", idFactura);

        Map<String, Object> outParam = new HashMap<>();
        Object objFactura = outParam.get("_factura");
        if(objFactura == null) return null;

        String strFactura = objFactura.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        FacturaPublica facturaPublica = objectMapper.readValue(strFactura, FacturaPublica.class);
        return facturaPublica;
    }

    @SneakyThrows
    @Override
    public List<CuentaReglaListado> cargaListadoReglasPorCuentaCliente(long idCuenta, long idCliente) {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Cuenta_Carga_Regla_Cuenta_Usuario");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCuenta", idCuenta);
        inParamMap.put("_idCliente", idCliente);

        Map<String, Object> outParam = new HashMap<>();
        Object objReglas = outParam.get("_resultado");
        if(objReglas == null) return null;

        String strReglas = objReglas.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        CuentaReglaListado[] reglas = objectMapper.readValue(strReglas, CuentaReglaListado[].class);
        return Arrays.asList(reglas);
    }

    @Override
    public ReglaCuentaSimplificado[] cargaReglaCuentaSimplificado(long idCliente, long idCuenta) throws JsonProcessingException {
        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withProcedureName("Carga_Reglas_Por_Cliente_Cuenta");
        Map<String, Object> inParamMap = new HashMap<>();
        inParamMap.put("_idCliente", idCliente);
        inParamMap.put("_idCuenta", idCuenta);

        Map<String, Object> outParamMap = new HashMap<>();
        Object objResultado = outParamMap.get("_resultado");
        if(objResultado == null) return null;
        String strResultado = objResultado.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        ReglaCuentaSimplificado[] reglas = objectMapper.readValue(strResultado, ReglaCuentaSimplificado[].class);
        return reglas;
    }

}
