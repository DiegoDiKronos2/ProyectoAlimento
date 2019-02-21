package com.example.a16diegovs.proyectoalimentos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Alimentacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacion);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
    }
}
