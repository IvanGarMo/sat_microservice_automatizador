package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacturaDespliegeCuenta {

    @JsonProperty("debe")
    private boolean debe;

    @JsonProperty("haber")
    private boolean haber;

    @JsonProperty("importe")
    private Double importe;

    @JsonProperty("codigoCuenta")
    private String codigoCuenta;

    @JsonProperty("descripcionOperacion")
    private String descripcionOperacion;

}
