package com.sat.serviciodescargamasiva.automatizador.automatizador.permisos;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="usuario")
@Immutable
class Usuario {
    @Id
    private long idUsuario;
    private String uidUserFirebase;
    private boolean activo;
    private boolean correoVerificado;

}
