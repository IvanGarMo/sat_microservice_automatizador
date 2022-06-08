package com.sat.serviciodescargamasiva.Automatizador;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sat.serviciodescargamasiva.Automatizador.ProcesadorFacturas.Json.FacturaJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;*/

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
@Slf4j
public class TestLecturaImpuestos {

    /*@Test
    public void testLecturaImpuestos() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File f = new File("/home/ivanovich/Downloads/Paquete_8_32616D78-BE9B-4E40-A5A8-0DCC95C198E7_01/1bfe8573-feb9-4c8b-87a1-60cb02d3b188.xml");
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(f);
        doc.getDocumentElement().normalize();
        NodeList nodeListEmisor = doc.getElementsByTagName("cfdi:Traslado");
        NodeList nodeListReceptor = doc.getElementsByTagName("cfdi:Receptor");
        System.out.println("Voy a leer los impuestos");
        if(nodeListEmisor.item(0) != null) {
            System.out.println("Voy a imprimir la lista de Traslado");
            int longitud = nodeListEmisor.item(0).getAttributes().getLength();
            for(int i=0; i<longitud; i++) {
                System.out.println("Contenido del emisor: "+nodeListEmisor.item(0).getAttributes().item(i));
            }
            //System.out.println("NodeListEmisor: "+nodeListEmisor.item(0).getAttributes().getNamedItem("Importe").getTextContent());
        }
        if(nodeListReceptor.item(0) != null) {
            System.out.println("Voy a imprimir la lista de Receptor");
            int longitud = nodeListReceptor.item(0).getAttributes().getLength();
            for(int i=0; i<longitud; i++) {
                System.out.println("Contenido del receptor: "+nodeListReceptor.item(0).getAttributes().item(i));
            }
            //System.out.println("NodeListReceptor: "+nodeListReceptor.item(0).getAttributes().getNamedItem("Importe").getTextContent());
        }
    }*/

    /*@Test
    public void testLecturaImpuestos() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File f = new File("/home/ivanovich/Downloads/Paquete_8_32616D78-BE9B-4E40-A5A8-0DCC95C198E7_01/1bfe8573-feb9-4c8b-87a1-60cb02d3b188.xml");
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(f);
        doc.getDocumentElement().normalize();
        NodeList conceptosNode = doc.getElementsByTagName("cfdi:Concepto");
        int longitud = conceptosNode.getLength();
        System.out.println("Cantidad de conceptos: "+longitud);
        for(int i=0; i<longitud; i++) {
            leeHijos(conceptosNode.item(i));
        }
    }*/

    /*private void leeHijos(Node node) {
        String nombreNodo = node.getNodeName();
        System.out.println("Trabajando con el nodo: "+nombreNodo+" claveProdServ: "+node.getAttributes().getNamedItem("ClaveProdServ").getTextContent());
        System.out.println("Cantidad de hijos del nodo "+nombreNodo+": "+node.getChildNodes().getLength());
        if(node.getChildNodes() != null && node.getChildNodes().getLength() > 0) {
            NodeList hijos = node.getChildNodes();
            int longitud = node.getChildNodes().getLength();
            for(int i=0; i<longitud; i++) {
                leeHijos(hijos.item(i));
            }
        }
    }*/

    @Test
    public void lecturaXml() throws ParserConfigurationException, IOException, SAXException {
        String folder = "/home/ivanovich/Desktop/XML DR DOMINGO";
        File[] files = new File(folder).listFiles();
        for(int i=0; i<files.length; i++) {
            System.out.println("-------------------------------------------------------------------");
            File f = files[i];
            System.out.println("Archivo: "+f.getAbsolutePath());
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            FacturaJson factura = xmlMapper.readValue(f, FacturaJson.class);
            System.out.println("FacturaJson: "+factura.toString());
        }
    }
}
