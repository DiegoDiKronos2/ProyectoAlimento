package com.example.a16diegovs.proyectoalimentos;

import android.widget.ImageView;

import java.io.Serializable;

public class Alimento implements Serializable {
    String Nombre,Azucar,Grasa,Sodio;
    boolean Liquid;

    public Alimento(String nombre, String azucar, String grasa, String sodio) {
        Nombre = nombre;
        Azucar = azucar;
        Grasa = grasa;
        Sodio = sodio;
    }

    public boolean isLiquid() {
        return Liquid;
    }

    public void setLiquid(boolean liquid) {
        Liquid = liquid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAzucar() {
        return Azucar;
    }

    public void setAzucar(String azucar) {
        Azucar = azucar;
    }

    public String getGrasa() {
        return Grasa;
    }

    public void setGrasa(String grasa) {
        Grasa = grasa;
    }

    public String getSodio() {
        return Sodio;
    }

    public void setSodio(String sodio) {
        Sodio = sodio;
    }
}
