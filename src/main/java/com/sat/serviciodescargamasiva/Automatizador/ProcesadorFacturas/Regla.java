package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas;

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
public class Regla implements Comparable<Regla> {
    public static final String RETENCION_ISR = "001";
    public static final String RETENCION_IVA = "002";

    private String codigoCuenta;
    private String descCuentaSat;
    private long claveProdServ;
    private String descClaveProdServ;
    private int nivel;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean impuestoAcreditable;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean impuestoTrasladado;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean impuestoRetenido;
    private double tasaRetenido;
    private String impuesto;


    public Regla(long claveProdServ) {
        this.claveProdServ = claveProdServ;
    }


    @Override
    public int compareTo(Regla o) {
        return Long.compare(this.claveProdServ, o.claveProdServ);
    }
}

class CustomBooleanDeserializer extends StdDeserializer<Boolean> {

    public CustomBooleanDeserializer(){ this(null); }

    protected CustomBooleanDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        return jsonParser.getText().equals("1");
    }
}
