/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sat.serviciodescargamasiva.Automatizador.googlecloud;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

//**
// *
// * @author IvanGarMo
// */
@Configuration
public class Config {
    @Autowired
    private GoogleHttpRequestInitializer httpRequestInitializer;
    @Bean
    public Storage configStorageClient() throws GeneralSecurityException, IOException {
        Storage storage = new Storage(GoogleNetHttpTransport.newTrustedTransport(), 
            new GsonFactory(), httpRequestInitializer);
        return storage;
    }
}
