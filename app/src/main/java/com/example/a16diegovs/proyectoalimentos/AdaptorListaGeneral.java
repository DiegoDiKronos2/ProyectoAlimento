package com.example.a16diegovs.proyectoalimentos;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptorListaGeneral extends ArrayAdapter {
    private Activity context;
    private ArrayList<Alimento>Alimentos;
    private int LayoutToUse;

    public AdaptorListaGeneral(@NonNull Activity context, @NonNull ArrayList<Alimento> Alimentos,int LayoutToUse) {
        super(context, R.layout.listaprincipal_row, Alimentos);
        this.context = context;
        this.Alimentos = Alimentos;
        this.LayoutToUse = LayoutToUse;
    }

    public View getView(int pos, View view, ViewGroup parent) {

        View fila = view;
        ViewHolder holder;
        if (fila == null) {
            LayoutInflater li = context.getLayoutInflater();
            fila = li.inflate(LayoutToUse, null);

            holder = new ViewHolder();
            holder.nombre = fila.findViewById(R.id.LR_Name);
            fila.setTag(holder);
        } else {
            holder = (ViewHolder) fila.getTag();
        }

        holder.nombre.setText(Alimentos.get(pos).getNombre());
        return fila;
    }

    static class ViewHolder {
        TextView nombre;
    }
}
