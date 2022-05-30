package com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

class PaqueteDescargadoBooleanDeserializer extends StdDeserializer<Boolean> {

    public PaqueteDescargadoBooleanDeserializer(){ this(null); }

    protected PaqueteDescargadoBooleanDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        return jsonParser.getText().equals("1");
    }
}
