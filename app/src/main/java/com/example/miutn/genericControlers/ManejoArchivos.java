package com.example.miutn.genericControlers;

import android.content.Context;

import java.io.BufferedReader;
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
}
