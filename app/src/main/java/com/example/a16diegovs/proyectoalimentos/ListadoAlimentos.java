package com.example.a16diegovs.proyectoalimentos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoAlimentos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Alimento> Alimentos = new ArrayList<>();
    ListView LW_General;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alimentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        LW_General = findViewById(R.id.LW_AlimentosGeneral);

        DBHelper Helper = new DBHelper(ListadoAlimentos.this);
        SQLiteDatabase BD = Helper.getReadableDatabase();
        String[] TABLAS = getResources().getStringArray(R.array.Tablas);

        for (int i = 0; i < getResources().getStringArray(R.array.Tablas).length; i++) {
            Cursor c = BD.rawQuery("SELECT * FROM '" + TABLAS[i] + "';", null);
            while (c.moveToNext()) {

                Alimento TEMP = new Alimento(c.getString(0), c.getFloat(1), c.getFloat(2), c.getFloat(3));
                Alimentos.add(TEMP);

            }
        }
        AdaptorListaGeneral a = new AdaptorListaGeneral(ListadoAlimentos.this, Alimentos);
        LW_General.setAdapter(a);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listado_alimentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cons_Beb) {
            // Handle the camera action
        } else if (id == R.id.Cons_2) {

        } else if (id == R.id.Cons_3) {

        } else if (id == R.id.Cons_4) {

        } else if (id == R.id.Cons_5) {

        } else if (id == R.id.Cons_6) {

        }
        item.setIcon(R.drawable.iconmenu_selected);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
