package com.sat.serviciodescargamasiva.Automatizador.Automatizador;

import com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas.CargadorFacturas;
import com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas.PaquetesNotFoundException;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.FacturaPueNotFoundException;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.ProcesadorFacturas;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Data
@NoArgsConstructor
public class Automatizador {
    @Autowired
    private CargadorFacturas cargadorFacturas;
    @Autowired
    private ProcesadorFacturas procesadorFacturas;

    public ResponseData contabilizaFacturas(long idDescarga, String rfcCliente, long idCliente, String rfcUidUserFirebase) {
        ResponseData rd = new ResponseData();
        try {
            List<File> archivos = cargadorFacturas.obtenFacturas(idDescarga);
            procesadorFacturas.procesaFacturas(archivos, rfcCliente, idCliente, rfcUidUserFirebase);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (FacturaPueNotFoundException e) {
            throw new RuntimeException(e);
        } catch(PaquetesNotFoundException e) {

        }

        return rd;
    }
}
