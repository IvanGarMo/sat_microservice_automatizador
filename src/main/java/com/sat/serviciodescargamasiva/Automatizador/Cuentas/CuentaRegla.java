package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CuentaRegla {
    private long idRegla;
    private long claveProdServ;
    private String descripcionProducto;
    private String descripcionCuenta;
    private String codigoCuenta;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean cuentaNivelUsuario;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean cuentaNivelCliente;
}
