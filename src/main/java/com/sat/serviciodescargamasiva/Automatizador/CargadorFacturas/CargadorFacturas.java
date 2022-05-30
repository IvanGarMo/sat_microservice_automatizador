package com.sat.serviciodescargamasiva.Automatizador.CargadorFacturas;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.api.services.storage.model.StorageObject;
import com.sat.serviciodescargamasiva.Automatizador.googlecloud.GoogleStorageOperations;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Data
@Service
public class CargadorFacturas {
    //Referencias a los ZIP relacionados con la descarga que almacenamos en la nube
    private int noZips;
    private ZipFactura[] zips;
    //Referencias a todas las facturas relacionadas con la descarga que almacenamos en la nube
    private int noFacturas;
    private ArrayList<File> facturas;
    @Autowired
    private OperacionesFacturaDB zipRepo;
    @Autowired
    private GoogleStorageOperations googleStorage;
    private String pathFiles;

    public ArrayList<File> obtenFacturas(long idDescarga) throws IOException, PaquetesNotFoundException,
            JsonProcessingException {
        //Obtenemos los zips almacenados en la base de datos
        this.zips = zipRepo.cargaZipFacturas(idDescarga);
        this.noZips = zips.length;
        //Procedo a descargar y descomprimir los zip en el folder especificado
        String folderDecompresion = zips[0].getFolder();
        if(!Files.exists(Paths.get(folderDecompresion))) {
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            Files.createDirectory(Paths.get(folderDecompresion), fileAttributes);
        }
        String folderTemp = folderDecompresion + "folder_" + zips[0].getIdDescarga() + "/";
        if(!Files.exists(Paths.get(folderTemp))) {
            Files.createDirectory(Paths.get(folderTemp));
        }
        String bucketName = googleStorage.getBucketFacturas();
        byte[] result;
        Path path;
        for (ZipFactura zip : zips) {
            String fileName = zip.getUrlPaquete();
            StorageObject objZip = googleStorage.download(fileName, bucketName);
            result = com.google.common.io.Files.toByteArray((File) objZip.get("file"));
            path = Paths.get(folderTemp + fileName);
            Files.write(path, result);
        }

        File[] files = new File(folderTemp).listFiles();

        //Procedo a descomprimir los archivos
        List<File> archivosZip = new ArrayList<>();
        for(int i=0; i<files.length; i++) {
            String fileZip = files[i].getAbsolutePath();
            File destDir = new File(folderTemp);
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = newFile(destDir, zipEntry);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zis.closeEntry();
            zis.close();
            archivosZip.add(files[i]);
        }

        //Ya que tengo los archivos, procedo a eliminar los zip
        for(File archivo : archivosZip) {
            archivo.delete();
        }

        //Registro las facturas creadas
        files = new File(folderTemp).listFiles();
        facturas = new ArrayList<File>();
        for (File file : files) {
            if (file.isFile()) {
                File refFile = new File(file.getName());
                facturas.add(refFile);
            }
        }



        return facturas;
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
