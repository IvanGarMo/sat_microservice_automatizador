package com.sat.serviciodescargamasiva.Automatizador.permisos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UsuarioClienteRelacionJpa extends CrudRepository<UsuarioClienteRelacion, Long> {
    Optional<UsuarioClienteRelacion> findUsuarioClienteRelacionByIdUsuarioAndIdCliente(long idUsuario, long idCliente);
}
