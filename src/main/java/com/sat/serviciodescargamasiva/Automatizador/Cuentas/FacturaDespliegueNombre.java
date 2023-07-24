package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacturaDespliegueNombre {

    @JsonProperty("rfc")
    private String rfc;

    @JsonProperty("emisor")
    private boolean emisor;

    @JsonProperty("receptor")
    private boolean receptor;

    @JsonProperty("idFactura")
    private String idFactura;

    @JsonProperty("cuenta")
    private List<FacturaDespliegeCuenta> cuenta;
}
