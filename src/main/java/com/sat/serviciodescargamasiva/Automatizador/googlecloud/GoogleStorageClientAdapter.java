/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sat.serviciodescargamasiva.Automatizador.googlecloud;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.StorageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 *
 * @author IvanGarMo
 */
@Service
public class GoogleStorageClientAdapter implements GoogleStorageOperations {
    @Autowired
    private Storage storage;
    @Value("${gcp.BUCKET_CERTIFICADO}")
    private String BUCKET_CERTIFICADO;
    @Value("${gcp.BUCKET_KEY}")
    private String BUCKET_KEY;

    @Value("${gcp.BUCKET_FACTURAS}")
    private String BUCKET_FACTURAS;

    public String getBucketCertificado() {
        return this.BUCKET_CERTIFICADO;
    }

    public String getBucketKey() {
        return this.BUCKET_KEY;
    }

    public String getBucketFacturas() { return this.BUCKET_FACTURAS; }

    public boolean upload(MultipartFile file, String fileName, String bucketName) throws IOException {
        StorageObject storageObject = new StorageObject();
        storageObject.setName(fileName);
        InputStream targetStream = new ByteArrayInputStream(file.getBytes());
        storage.objects().insert(bucketName, storageObject,
                new AbstractInputStreamContent(fileName) {
            @Override
            public long getLength() throws IOException {
                return file.getSize();
            }

        @Override
            public boolean retrySupported() {
                return false;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return targetStream;
            }
        }).execute();
        return true;
    }
    
    public StorageObject download(String fileName, String bucketName) throws IOException {
        StorageObject storageObject = storage.objects().get(bucketName, fileName).execute();
        File file = new File("./"+fileName);
        FileOutputStream os = new FileOutputStream(file);
     
        storage.getRequestFactory()
                .buildGetRequest(new GenericUrl(storageObject.getMediaLink()))
                .execute()
                .download(os);
        storageObject.set("file", file);
        return storageObject;
    }
}
