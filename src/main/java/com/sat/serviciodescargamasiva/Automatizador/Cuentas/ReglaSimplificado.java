package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.Data;

import java.io.IOException;

@Data
public class ReglaSimplificado {
    private long id;
    private long claveProdServ;
    private String descripcion;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean activo;
    private long idCliente;
}
