package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comprobante {
    private String cfdi;
    private String xsi;
    private String Certificado;
    private String Fecha;
    private String Folio;
    private String FormaPago;
    private String LugarExpedicion;
    private String MetodoPago;
    private String Moneda;
    private String NoCertificado;
    private String Sello;
    private String SubTotal;
    private String TipoCambio;
    private String TipoDeComprobante;
    private String Total;
    private String Version;
    private String schemaLocation;
}
