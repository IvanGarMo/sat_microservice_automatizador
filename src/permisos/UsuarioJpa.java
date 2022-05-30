package com.sat.serviciodescargamasiva.automatizador.automatizador.permisos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

interface UsuarioJpa extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUidUserFirebase(String uidUserFirebase);
}
