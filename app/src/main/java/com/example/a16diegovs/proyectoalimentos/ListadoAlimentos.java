package com.example.a16diegovs.proyectoalimentos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListadoAlimentos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Alimento> Alimentos = new ArrayList<>();
    ListView LW_General;
    SQLiteDatabase BD;
    String[] TABLAS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alimentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        LW_General = findViewById(R.id.LW_AlimentosGeneral);

        DBHelper Helper = new DBHelper(ListadoAlimentos.this);
        BD = Helper.getReadableDatabase();
        TABLAS = getResources().getStringArray(R.array.Tablas);
        ListLoad();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cons_0) {
            ListLoad();
        } else if (id == R.id.Cons_1) {
            ListLoad(TABLAS[0]);
        } else if (id == R.id.Cons_2) {
            ListLoad(TABLAS[1]);
        } else if (id == R.id.Cons_3) {
            ListLoad(TABLAS[2]);
        } else if (id == R.id.Cons_4) {
            ListLoad(TABLAS[3]);
        } else if (id == R.id.Cons_5) {
            ListLoad(TABLAS[4]);
        } else if (id == R.id.Cons_6) {
            ListLoad(TABLAS[5]);
        } else if (id == R.id.Cons_7) {
            ListLoad(TABLAS[6]);
        } else if (id == R.id.Cons_8) {
            ListLoad(TABLAS[7]);
        }
        item.setIcon(R.drawable.iconmenu_selected);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ListLoad(String Tabla) {//Carga del listado de alimentos filtrado por tabla
        Alimentos.clear();
        Cursor c = BD.rawQuery("SELECT * FROM '" + Tabla + "';", null);
        while (c.moveToNext()) {
            Alimento TEMP = new Alimento(c.getString(0), c.getFloat(1), c.getFloat(2), c.getFloat(3));
            Alimentos.add(TEMP);
        }
        AdaptorListaGeneral a = new AdaptorListaGeneral(ListadoAlimentos.this, Alimentos);
        LW_General.setAdapter(a);
    }

    public void ListLoad() {//Carga del listado de alimentos total
        Alimentos.clear();
        for (int i = 0; i < getResources().getStringArray(R.array.Tablas).length; i++) {
            Cursor c = BD.rawQuery("SELECT * FROM '" + TABLAS[i] + "';", null);
            while (c.moveToNext()) {
                Alimento TEMP = new Alimento(c.getString(0), c.getFloat(1), c.getFloat(2), c.getFloat(3));
                Alimentos.add(TEMP);
            }
        }
        AdaptorListaGeneral a = new AdaptorListaGeneral(ListadoAlimentos.this, Alimentos);
        LW_General.setAdapter(a);
        LW_General.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListadoAlimentos.this, "Wha?", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder Details = new AlertDialog.Builder(ListadoAlimentos.this);
                LayoutInflater inflater = ListadoAlimentos.this.getLayoutInflater();
                Details.setView(inflater.inflate(R.layout.detailedfood,null));
                Details.create();
                Details.show();
            }
        });
    }
}
