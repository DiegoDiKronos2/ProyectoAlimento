package com.example.a16diegovs.proyectoalimentos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class Alimentacion extends AppCompatActivity {

    Button BT_Listado, BT_Menu;
    ArrayList<Alimento> Menu = new ArrayList<>();
    final int ID_LISTA_GENERAL = 0,
            ID_MENU = 1,
            ID_DIRECT_TO_OTHER = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacion);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        BT_Listado = findViewById(R.id.BT_ConsultaA);
        BT_Menu = findViewById(R.id.BT_ForMenu);


        BT_Listado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BT_Listado.setBackgroundResource(R.drawable.backg_verde);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    BT_Listado.setBackgroundResource(R.drawable.texto_bonito);
                    ToAlim();
                }
                return false;
            }
        });
        BT_Menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BT_Menu.setBackgroundResource(R.drawable.backg_verde);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    BT_Menu.setBackgroundResource(R.drawable.texto_bonito);
                    ToMenu();
                }
                return false;
            }
        });

    }
    public void ToAlim(){
        Intent i = new Intent(Alimentacion.this,ListadoAlimentos.class);
        i.putExtra("Menu", Menu);
        startActivityForResult(i,ID_LISTA_GENERAL);
    }
    public void ToMenu(){
        Intent i = new Intent(Alimentacion.this,Menus.class);
        i.putExtra("Menu", Menu);
        startActivityForResult(i,ID_MENU);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Menu = (ArrayList<Alimento>) data.getExtras().getSerializable("Menu");
    }
}
