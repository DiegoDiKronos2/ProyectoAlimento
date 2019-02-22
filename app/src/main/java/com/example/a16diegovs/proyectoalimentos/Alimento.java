package com.example.a16diegovs.proyectoalimentos;

import android.widget.ImageView;

public class Alimento {
    String Nombre;
    float Azucar,Grasa,Sodio;
    boolean Liquid;

    public Alimento(String nombre, float azucar, float grasa, float sodio) {
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

    public float getAzucar() {
        return Azucar;
    }

    public void setAzucar(float azucar) {
        Azucar = azucar;
    }

    public float getGrasa() {
        return Grasa;
    }

    public void setGrasa(float grasa) {
        Grasa = grasa;
    }

    public float getSodio() {
        return Sodio;
    }

    public void setSodio(float sodio) {
        Sodio = sodio;
    }
}
