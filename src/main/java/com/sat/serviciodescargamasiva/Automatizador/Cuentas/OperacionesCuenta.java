package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.FacturaPublica;

import java.util.List;

public interface OperacionesCuenta {
    ResponseData creaCuenta(Cuenta cuenta);
    ResponseData eliminaCuenta(long idCuenta);
    Cuenta[] cargaCuentas(long idUsuario, long idCliente) throws JsonProcessingException;
    ClienteInfoBasica[] cargaClientes(long idUsuario) throws JsonProcessingException;
    ResponseData actualizaNombreCuenta(long idCuenta, String descripcion);
    String cargaCuentasParaDespliegue(String uidUserFirebase);
    Iterable<CategoriaCuenta> cargaCategoriasCuentas();
    CuentaRegla[] cargaReglasCuenta(BusquedaCuentaRegla busqCuentaRegla) throws JsonProcessingException;
    ReglaSimplificado[] cargaPendientes(long idDescarga) throws JsonProcessingException;
    Cuenta[] cuentasPorCategoriaCliente(long idCategoria, long idUsuario, long idCliente) throws JsonProcessingException;
    ClienteSimplificado cargaClienteSimplificado(long idSolicitud);
    DetallesImplementacion cargaDetallesContabilidad(long idSolicitud);
    List<FacturaDespliegue> cargaFacturasDespliegue(long idSolicitud) throws JsonProcessingException;
    FacturaPublica cargaFactura(long idFactura) throws JsonProcessingException;
}
