package com.example.a16diegovs.proyectoalimentos;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Alimentacion extends AppCompatActivity {

    Button BT_Listado, BT_About;
    ArrayList<Alimento> Menu = new ArrayList<>();
    final int ID_LISTA_GENERAL = 0;
    static ArrayList<Alimento> LFrutas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacion);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        BT_Listado = findViewById(R.id.BT_ConsultaA);
        BT_About = findViewById(R.id.BT_About);
        ConstraintLayout MP = findViewById(R.id.MPrincipal);
        MP.getBackground().setAlpha(80);
        ChargeFList();

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

        BT_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog Details = new Dialog(Alimentacion.this);
                Details.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Details.setContentView(R.layout.about);
                Details.show();
            }
        });

    }
    public void ToAlim(){
        Intent i = new Intent(Alimentacion.this,ListadoAlimentos.class);
        i.putExtra("Menu", Menu);
        startActivityForResult(i,ID_LISTA_GENERAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Menu = (ArrayList<Alimento>) data.getExtras().getSerializable("Menu");
    }

    public void ChargeFList(){
        DBHelper Helper = new DBHelper(Alimentacion.this);
        SQLiteDatabase BD = Helper.getReadableDatabase();
        Cursor c = BD.rawQuery("SELECT * FROM 'froita';", null);
        while (c.moveToNext()) {
            Alimento TEMP = new Alimento(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
            LFrutas.add(TEMP);
        }
    }
}
