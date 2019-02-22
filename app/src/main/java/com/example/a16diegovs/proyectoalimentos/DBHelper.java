package com.example.a16diegovs.proyectoalimentos;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DBNAME = "alimentos.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }
}
