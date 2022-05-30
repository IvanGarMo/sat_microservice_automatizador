/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sat.serviciodescargamasiva.Automatizador.googlecloud;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;

/**
 *
 * @author IvanGarMo
 */
@Service
@Data
public class GoogleHttpRequestInitializer implements HttpRequestInitializer {
    private GoogleCredentials googleCredentials;
    private HttpCredentialsAdapter adapter;
    @Value("${gcp.PROPERTIES_FILE}")
    public String PROPERTIES_FILE;

    @Override
    public void initialize(HttpRequest hr) throws IOException {
        //Elimino el punto que es añadido al final del directorio
        String pathRaiz = FileSystems.getDefault().getPath(".").toAbsolutePath().toString().replace(".", "");
        String pathResources = "src/main/resources/";
        String pathCompleto = pathRaiz+pathResources+PROPERTIES_FILE;
        googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(pathCompleto))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        adapter = new HttpCredentialsAdapter(googleCredentials);
        
        adapter.initialize(hr);
        hr.setConnectTimeout(60000);
        hr.setReadTimeout(60000);
    }
}
