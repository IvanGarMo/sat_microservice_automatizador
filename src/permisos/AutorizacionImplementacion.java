package com.sat.serviciodescargamasiva.automatizador.automatizador.permisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizacionImplementacion implements Autorizacion {
    @Autowired
    private UsuarioJpa userRepo;
    @Autowired
    private UsuarioClienteRelacionJpa userClienteRepo;

    @Override
    public boolean tieneAccesoCliente(long idUsuario, long idCliente) {
        System.out.println("TieneAccesoCliente - Usuario "+idUsuario+" Cliente: "+idCliente);
        return userClienteRepo.findUsuarioClienteRelacionByIdUsuarioAndIdCliente(idUsuario, idCliente).isPresent();
    }

    @Override
    public long cargaIdUsaurio(String uidUserFirebase) {
        return userRepo.findUsuarioByUidUserFirebase(uidUserFirebase).get().getIdUsuario();
    }

    @Override
    public boolean estaUsuarioActivo(String uidUserFirebase) {
        return userRepo.findUsuarioByUidUserFirebase(uidUserFirebase).get().isActivo();
    }

    @Override
    public boolean estaCorreoVerificado(String uidUserFirebase) {
        return userRepo.findUsuarioByUidUserFirebase(uidUserFirebase).get().isCorreoVerificado();
    }
}
