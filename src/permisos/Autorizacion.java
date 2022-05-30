package com.sat.serviciodescargamasiva.automatizador.automatizador.permisos;

public interface Autorizacion {
    boolean tieneAccesoCliente(long idUsuario, long idCliente);
    long cargaIdUsaurio(String uidUserFirebase);
    boolean estaUsuarioActivo(String uidUserFirebase);
    boolean estaCorreoVerificado(String uidUserFirebase);
}
