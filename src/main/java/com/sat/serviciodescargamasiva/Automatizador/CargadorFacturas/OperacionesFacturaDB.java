package com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas;

import com.fasterxml.jackson.core.JsonProcessingException;

interface OperacionesFacturaDB {
    ZipFactura[] cargaZipFacturas(long idDescarga) throws JsonProcessingException, PaquetesNotFoundException;
}
