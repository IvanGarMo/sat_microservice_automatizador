package com.sat.serviciodescargamasiva.Automatizador.Automatizador;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseData {
    private boolean opValida;
    private List<String> mensajes;

    public ResponseData() {
        this.mensajes = new ArrayList<>();
    }

    public void addMensaje(String mensaje) {
        this.mensajes.add(mensaje);
    }

    public void print() {
        System.out.println("OpValida: "+opValida);
        System.out.println("-----------------------------");
        mensajes.forEach(mensaje -> System.out.println(mensaje));
        System.out.println("-----------------------------");
    }
}
