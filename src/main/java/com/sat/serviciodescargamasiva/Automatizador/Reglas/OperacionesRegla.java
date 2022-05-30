package com.sat.serviciodescargamasiva.Automatizador.Reglas;


import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;

public interface OperacionesRegla {
    ResponseData creaReglaNivelUsuario(String uidUserFirebase, long idCuenta, long claveProdServ);
    ResponseData creaReglaNivelCliente(long idCliente, long idCuenta, long claveProdServ);
    ResponseData eliminaReglaNivelUsuario(long idRegla);
    ResponseData eliminaReglaNivelCliente(long idRegla);
}
