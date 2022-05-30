package com.sat.serviciodescargamasiva.automatizador.automatizador.permisos;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="Vista_Cliente_Usuario")
@Immutable
class UsuarioClienteRelacion {
    @Id
    private long idCliente;
    private long idUsuario;
    private String uidUserFirebase;
}
