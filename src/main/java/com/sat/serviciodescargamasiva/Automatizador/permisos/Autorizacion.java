package com.sat.serviciodescargamasiva.Automatizador.permisos;

public interface Autorizacion {
    boolean tieneAccesoCliente(long idUsuario, long idCliente);
    long cargaIdUsaurio(String uidUserFirebase);
    boolean estaUsuarioActivo(String uidUserFirebase);
    boolean estaCorreoVerificado(String uidUserFirebase);
}
