package com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturaJson {
    public static final String PAGO_EN_UNA_SOLA_EXHIBICION = "PUE";
    public static final String PAGO_DIFERIDO = "PPD";

    @JsonProperty("Certificado")
    private String certificado;
    @JsonProperty("Fecha")
    private String fecha;
    @JsonProperty("Folio")
    private String folio;
    @JsonProperty("FormaPago")
    private String formaPago;
    @JsonProperty("LugarExpedicion")
    private String lugarExpedicion;
    @JsonProperty("MetodoPago")
    private String metodoPago;
    @JsonProperty("Moneda")
    private String moneda;
    @JsonProperty("NoCertificado")
    private String noCertificado;
    @JsonProperty("Sello")
    private String sello;
    @JsonProperty("SubTotal")
    private String subtotal;
    @JsonProperty("TipoCambio")
    private String tipoCambio;
    @JsonProperty("TipoDeComprobante")
    private String tipoDeComprobante;
    @JsonProperty("Total")
    private String total;
    @JsonProperty("Version")
    private String version;
    private String schemaLocation;
    @JsonProperty("Emisor")
    private Emisor emisor;
    @JsonProperty("Receptor")
    private Receptor receptor;
    @JsonProperty("Conceptos")
    private Concepto[] conceptos;
}
