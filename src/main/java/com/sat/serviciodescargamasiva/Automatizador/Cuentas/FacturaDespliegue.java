package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Getter
@Setter
public class FacturaDespliegue {

    @JsonProperty("idFactura")
    private long idFactura;

    @JsonProperty("nombreFactura")
    private FacturaDespliegueNombre nombreFactura;

}
