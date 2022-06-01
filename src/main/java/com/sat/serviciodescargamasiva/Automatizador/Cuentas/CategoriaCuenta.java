package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="automatizador_categoria_cuenta")
@Data
public class CategoriaCuenta {
    @Id
    private int id;
    private String descripcion;
}
