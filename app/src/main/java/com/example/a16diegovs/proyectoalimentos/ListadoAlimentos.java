package com.example.a16diegovs.proyectoalimentos;

import android.app.Dialog;
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
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListadoAlimentos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Alimento> Alimentos = new ArrayList<>();
    ListView LW_General;
    SQLiteDatabase BD;
    String[] TABLAS;

    //LIMITS
    private float L_Am_Azucar = 5f;
    private float L_Rj_Azucar = 10f;
    private float L_Am_Grasas = 1.5f;
    private float L_Rj_Grasas = 5f;
    private float L_Am_Sodio = 120f;
    private float L_Rj_Sodio = 600f;
    private int V_Azucar;
    private int V_Grasas;
    private int V_Sodio;

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
            //ListLoad(TABLAS[7]);
            Toast.makeText(this, "Jaja, a la BD no le gustan las verduras sorry", Toast.LENGTH_SHORT).show();
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
            Alimento TEMP = new Alimento(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
            Alimentos.add(TEMP);
        }
        AdaptorListaGeneral a = new AdaptorListaGeneral(ListadoAlimentos.this, Alimentos);
        AdaptorCreator(a);
    }

    public void ListLoad() {//Carga del listado de alimentos total
        Alimentos.clear();
        for (int i = 0; i+1 < getResources().getStringArray(R.array.Tablas).length; i++) {
            Cursor c = BD.rawQuery("SELECT * FROM '" + TABLAS[i] + "';", null);
            while (c.moveToNext()) {
                Alimento TEMP = new Alimento(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
                Alimentos.add(TEMP);
                Log.println(Log.DEBUG,"ID",Alimentos.get(0).getNombre());
            }
            c.close();
        }
        AdaptorListaGeneral a = new AdaptorListaGeneral(ListadoAlimentos.this, Alimentos);
        AdaptorCreator(a);
    }
    public void AdaptorCreator(AdaptorListaGeneral a){
        LW_General.setAdapter(a);
        LW_General.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.println(Log.DEBUG,"ID",""+i);
                Dialog Details = new Dialog(ListadoAlimentos.this);
                Details.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Details.setContentView(R.layout.detailedfood);
                TextView Nombre = Details.findViewById(R.id.TW_Name);
                Nombre.setText(Alimentos.get(i).getNombre());
                TextView Azucar = Details.findViewById(R.id.TW_Azucar);
                TextView Grasas = Details.findViewById(R.id.TW_Grasas);
                TextView Sodio = Details.findViewById(R.id.TW_Sodio);
                float CAzucar,CGrasas,CSodio;
                String Trazas = "";
                try{

                    CAzucar = Float.parseFloat(Alimentos.get(i).getAzucar().replace(',','.'));
                }catch (Exception X){
                    CAzucar = 0;
                    Trazas = Trazas+"Azucar";
                }
                try{
                    CGrasas = Float.parseFloat(Alimentos.get(i).getGrasa().replace(',','.'));
                }catch (Exception X){
                    CGrasas = 0;
                    if(Trazas.equals("")){
                        Trazas = Trazas+"Grasas";
                    }else{
                        Trazas = Trazas+",Grasas";
                    }
                }
                try{
                    CSodio = Float.parseFloat(Alimentos.get(i).getSodio().replace(',','.'));
                }catch (Exception X){
                    CSodio = 0;
                    if(Trazas.equals("")){
                        Trazas = Trazas+"Sodio";
                    }else{
                        Trazas = Trazas+" y Sodio";
                    }
                }
                Azucar.setText(""+CAzucar);
                Grasas.setText(""+CGrasas);
                Sodio.setText(""+CSodio);
                Salud(CAzucar,CGrasas,CSodio,Nombre);

                Details.show();
            }
        });
    }

    public void Salud(float Azucar, float Grasas, float Sodio, TextView Nombre){
        if(Azucar <= L_Am_Azucar){
            V_Azucar = 1;
        }else if(Azucar <= L_Rj_Azucar){
            V_Azucar = 2;
        }else{
            V_Azucar = 3;
        }

        if(Grasas <= L_Am_Grasas){
            V_Grasas = 1;
        }else if(Grasas <= L_Rj_Grasas){
            V_Grasas = 2;
        }else{
            V_Grasas = 3;
        }

        if(Sodio <= L_Am_Sodio){
            V_Sodio = 1;
        }else if(Sodio <= L_Rj_Sodio){
            V_Sodio = 2;
        }else{
            V_Sodio = 3;
        }

        int RES = V_Azucar+V_Grasas+V_Sodio;

        if(RES == 3){
            Nombre.setBackgroundResource(R.drawable.backg_verde);
        }else if(RES <= 5){
            Nombre.setBackgroundResource(R.drawable.backg_amarillo);
        }else{
            if(V_Sodio == 2 && V_Grasas == 2 && V_Azucar == 2){
                Nombre.setBackgroundResource(R.drawable.backg_amarillo);
            }else{
                Nombre.setBackgroundResource(R.drawable.backg_rojo);
            }
        }
    }
}
