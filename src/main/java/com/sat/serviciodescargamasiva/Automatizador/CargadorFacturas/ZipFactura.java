package com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;


@Data
@NoArgsConstructor
@AllArgsConstructor
class ZipFactura {
    @JsonProperty("idRegistro")
    private long idRegistro;
    @JsonProperty("idDescarga")
    private long idDescarga;
    @JsonProperty("idPaquete")
    private String idPaqueteSAT;
    @JsonProperty("paqueteDescargado")
    @JsonDeserialize(using = PaqueteDescargadoBooleanDeserializer.class)
    private boolean archivoDescargado;
    @JsonProperty("urlPaquete")
    private String urlPaquete;
    @JsonProperty("folder")
    private String folder;
}

