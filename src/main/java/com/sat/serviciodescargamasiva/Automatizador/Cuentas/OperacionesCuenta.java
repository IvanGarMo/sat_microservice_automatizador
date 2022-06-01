package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;

public interface OperacionesCuenta {
    ResponseData creaCuenta(Cuenta cuenta);
    ResponseData eliminaCuenta(long idCuenta);
    Cuenta[] cargaCuentas(long idUsuario, long idCliente) throws JsonProcessingException;
    ClienteInfoBasica[] cargaClientes(long idUsuario) throws JsonProcessingException;
    ResponseData actualizaNombreCuenta(long idCuenta, String descripcion);
    String cargaCuentasParaDespliegue(String uidUserFirebase);
    Iterable<CategoriaCuenta> cargaCategoriasCuentas();
}
