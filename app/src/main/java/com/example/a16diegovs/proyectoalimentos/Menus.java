package com.example.a16diegovs.proyectoalimentos;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Menus extends AppCompatActivity {

    ArrayList<Alimento> Menu;
    ListView LW_Menu;
    Button BT_M_Add,BT_M_Del,TW_Gen;
    TextView TW_Az,TW_Gr,TW_So;
    LinearLayout LLAz,LLGr,LLSo;

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
        setContentView(R.layout.activity_menus);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        Menu = (ArrayList<Alimento>) getIntent().getSerializableExtra("Menu");
        setResult(RESULT_OK,getIntent());
        BT_M_Add = findViewById(R.id.BT_Menu_Add);
        BT_M_Del = findViewById(R.id.BT_Menu_Del);
        LW_Menu = findViewById(R.id.LW_Menus);
        LLAz = findViewById(R.id.LL_Azucar);
        LLGr = findViewById(R.id.LL_Grasa);
        LLSo = findViewById(R.id.LL_Sodio);
        TW_Az = findViewById(R.id.TW_M_Az);
        TW_Gr = findViewById(R.id.TW_M_Gr);
        TW_So = findViewById(R.id.TW_M_So);
        TW_Gen = findViewById(R.id.TW_M_General);

        ChekOnMenu();

        BT_M_Add.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BT_M_Add.setBackgroundResource(R.drawable.backg_verde);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    BT_M_Add.setBackgroundResource(R.drawable.texto_bonito);
                    Intent i = new Intent(Menus.this,ListadoAlimentos.class);
                    i.putExtra("Menu", Menu);
                    startActivityForResult(i,1);
                }
                return false;
            }
        });
        BT_M_Del.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BT_M_Del.setBackgroundResource(R.drawable.backg_verde);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    BT_M_Del.setBackgroundResource(R.drawable.texto_bonito);
                    Menu.clear();
                    Intent A = new Intent();
                    A.putExtra("Menu",Menu);
                    setResult(RESULT_OK,A);

                    AdaptorListaGeneral a = new AdaptorListaGeneral(Menus.this, Menu,R.layout.lista_row_lite);
                    LW_Menu.setAdapter(a);

                    ChekOnMenu();
                }
                return false;
            }
        });


        AdaptorListaGeneral a = new AdaptorListaGeneral(Menus.this, Menu,R.layout.lista_row_lite);
        LW_Menu.setAdapter(a);
        LW_Menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Dialog Details = new Dialog(Menus.this);
                Details.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Details.setContentView(R.layout.detailedfood);
                TextView Nombre = Details.findViewById(R.id.TW_Name);
                Nombre.setText(Menu.get(i).getNombre());
                TextView Azucar = Details.findViewById(R.id.TW_Azucar);
                TextView Grasas = Details.findViewById(R.id.TW_Grasas);
                TextView Sodio = Details.findViewById(R.id.TW_Sodio);
                final Button Add = Details.findViewById(R.id.BT_Add);
                Add.setText(R.string.Remove);
                float[] Values;
                Values = Conversor(Menu.get(i).getAzucar(),Menu.get(i).getGrasa(),Menu.get(i).getSodio());
                if(Values[0] >= 0){
                    Azucar.setText(String.format("%.2f", Values[0]));
                }else{
                    Azucar.setText("Trazas");
                }
                if(Values[1] >= 0){
                    Grasas.setText(String.format("%.2f", Values[1]));
                }else{
                    Grasas.setText("Trazas");
                }
                if(Values[2] >= 0){
                    Sodio.setText(String.format("%.2f", Values[2]));
                }else{
                    Sodio.setText("Trazas");
                }
                Salud(Values[0],Values[1],Values[2],Nombre,null,null,null);

                Add.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            Add.setBackgroundResource(R.drawable.backg_verde);
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            Add.setBackgroundResource(R.drawable.texto_bonito);
                            Menu.remove(i);
                            AdaptorListaGeneral a = new AdaptorListaGeneral(Menus.this, Menu,R.layout.lista_row_lite);
                            LW_Menu.setAdapter(a);
                        }
                        return false;
                    }
                });
                Details.show();
            }
        });


    }
    public void ChekOnMenu(){
        if(!Menu.isEmpty()){
            TW_Gen.setText(R.string.Calc);
            TW_Gen.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        TW_Gen.setBackgroundResource(R.drawable.backg_verde);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        TW_Gen.setBackgroundResource(R.drawable.texto_bonito);
                        float SAz = 0,SGr = 0,SSo = 0;
                        byte contador = 0;
                        for (Alimento A : Menu) {
                            float[] Values = Conversor(A.getAzucar(),A.getGrasa(),A.getSodio());
                            SAz = SAz + Values[0];
                            SGr = SGr + Values[1];
                            SSo = SSo + Values[2];
                            contador++;
                        }
                        SAz = SAz/contador;
                        SGr = SGr/contador;
                        SSo = SSo/contador;

                        TW_Az.setText(String.format("%.2f", SAz));
                        TW_Gr.setText(String.format("%.2f", SGr));
                        TW_So.setText(String.format("%.2f", SSo));

                        Salud(SAz,SGr,SSo,TW_Gen,LLAz,LLGr,LLSo);
                    }
                    return false;
                }
            });
        }else {
            TW_Gen.setText(R.string.MenuVacio);
            TW_Gen.setBackgroundResource(R.drawable.texto_bonito);
            LLAz.setBackgroundResource(R.drawable.texto_bonito);
            LLGr.setBackgroundResource(R.drawable.texto_bonito);
            LLSo.setBackgroundResource(R.drawable.texto_bonito);
            TW_Az.setText("");
            TW_Gr.setText("");
            TW_So.setText("");
        }
    }

    public void Salud(float Azucar, float Grasas, float Sodio, View Nombre, @Nullable View Az,@Nullable View Gr,@Nullable View So){
        if(Azucar <= L_Am_Azucar){
            V_Azucar = 1;
            if(Az != null){
                Az.setBackgroundResource(R.drawable.backg_verde);
            }
        }else if(Azucar <= L_Rj_Azucar){
            V_Azucar = 2;
            if(Az != null){
                Az.setBackgroundResource(R.drawable.backg_amarillo);
            }
        }else{
            V_Azucar = 3;
            if(Az != null){
                Az.setBackgroundResource(R.drawable.backg_rojo);
            }
        }

        if(Grasas <= L_Am_Grasas){
            V_Grasas = 1;
            if(Gr != null){
                Gr.setBackgroundResource(R.drawable.backg_verde);
            }
        }else if(Grasas <= L_Rj_Grasas){
            V_Grasas = 2;
            if(Gr != null){
                Gr.setBackgroundResource(R.drawable.backg_amarillo);
            }
        }else{
            V_Grasas = 3;
            if(Gr != null){
                Gr.setBackgroundResource(R.drawable.backg_rojo);
            }
        }

        if(Sodio <= L_Am_Sodio){
            V_Sodio = 1;
            if(So != null){
                So.setBackgroundResource(R.drawable.backg_verde);
            }
        }else if(Sodio <= L_Rj_Sodio){
            V_Sodio = 2;
            if(So != null){
                So.setBackgroundResource(R.drawable.backg_amarillo);
            }
        }else{
            V_Sodio = 3;
            if(So != null){
                So.setBackgroundResource(R.drawable.backg_rojo);
            }
        }

        int RES = V_Azucar+V_Grasas+V_Sodio;

        if(RES == 3){
            Nombre.setBackgroundResource(R.drawable.backg_verde);
            if(Nombre.getId()==R.id.TW_M_General){
                TW_Gen.setText(R.string.MenuV);
            }
        }else if(RES <= 5){
            Nombre.setBackgroundResource(R.drawable.backg_amarillo);
            if(Nombre.getId()==R.id.TW_M_General){
                TW_Gen.setText(R.string.MenuA);
            }
        }else{
            if(V_Sodio == 2 && V_Grasas == 2 && V_Azucar == 2){
                Nombre.setBackgroundResource(R.drawable.backg_amarillo);
                if(Nombre.getId()==R.id.TW_M_General){
                    TW_Gen.setText(R.string.MenuA);
                }
            }else{
                Nombre.setBackgroundResource(R.drawable.backg_rojo);
                if(Nombre.getId()==R.id.TW_M_General){
                    TW_Gen.setText(R.string.MenuR);
                }
            }
        }
    }

    public float[] Conversor(String Azucar, String Grasa, String Sodio){
        float[] Values = new float[3];
        try{
            Values[0] = Float.parseFloat(Azucar.replace(',','.'));
        }catch (Exception X){
            Values[0] = -1;
        }
        try{
            Values[1] = Float.parseFloat(Grasa.replace(',','.'));
        }catch (Exception X){
            Values[1] = -1;
        }
        try{
            Values[2] = Float.parseFloat(Sodio.replace(',','.'));
        }catch (Exception X){
            Values[2] = -1;
        }
        return Values;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Menu = (ArrayList<Alimento>) data.getExtras().getSerializable("Menu");
        AdaptorListaGeneral a = new AdaptorListaGeneral(Menus.this, Menu,R.layout.lista_row_lite);
        LW_Menu.setAdapter(a);
        setResult(RESULT_OK,data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChekOnMenu();
    }
}
