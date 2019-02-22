package com.example.a16diegovs.proyectoalimentos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Alimentacion extends AppCompatActivity {

    Button BT_Listado, BT_Menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacion);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        BT_Listado = findViewById(R.id.BT_ConsultaA);
        BT_Menu = findViewById(R.id.BT_ForMenu);


        BT_Listado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Alimentacion.this,listado_principal.class);
                startActivity(i);
            }
        });
        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
