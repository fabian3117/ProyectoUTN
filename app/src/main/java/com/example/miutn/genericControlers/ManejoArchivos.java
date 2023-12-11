package com.example.miutn.genericControlers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.miutn.activitys.VistaMarkdown;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManejoArchivos {
    public void CrearArchivo(Context context,String filename,String fileContents) throws FileNotFoundException {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String leerArchivo(Context context,String filename) {
        StringBuilder contenido = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String linea;

            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contenido.toString();
    }
    public String controladorArchivo(String nameDocument,Context context) throws IOException {
String salida="";
        //-->   Version mejorada donde verificamos en local o guardamos <--
        String salidaA=leerArchivo(context,nameDocument);
        if(salidaA.contains("")||salidaA.contains("Excepcion")){
            //-->   Archivo no encontrado   <--
            //-->   Descargo    <--
            TestDescargarArchivos(nameDocument,context);
        }
        return salida;
    }
    public void TestDescargarArchivos(String archivo,Context context) throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference pathReference = storageRef.child(archivo);
        // StorageReference gsReference = firebaseStorage.getReferenceFromUrl("gs://miutn-1834d.appspot.com"+archivo);
        File localFile = File.createTempFile(archivo.replace(".md",""), "md");

        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Log.e("FIREBASE",archivo);
                Log.e("FIREBASE","taskSnapshot.");
                if(taskSnapshot.getTask().isSuccessful()){
                    //-->   Save Files  <--
                    try {
                        CrearArchivo(context,archivo,localFile.toString());
                        //-->
                      //  VistaMarkdown.ActualizarInterfaz();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("FIREBASE","ERROR");
            }
        });

    }

}
