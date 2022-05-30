package com.sat.serviciodescargamasiva.Automatizador.googlecloud;

import com.google.api.services.storage.model.StorageObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GoogleStorageOperations {
    String getBucketCertificado();
    String getBucketKey();
    String getBucketFacturas();
    boolean upload(MultipartFile file, String fileName, String bucketName) throws IOException;
    StorageObject download(String fileName, String bucketName) throws IOException;
}
