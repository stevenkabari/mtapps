package com.example.mtapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MTAPPS_DB";
    private static final String INCOME  = "INCOME_TABLE";
    private static final String EXPENSES  = "EXPENSES_TABLE";
    private static final String NAME = "name";
    private static final String DESC = "description";
    private static final String COST = "cost_per_unit";
    private static final String NUMBER = "quantity";
    private static final String DATE = "date";
    private static final String TOTAL = "total";

    private final Context context;

    public DBaseHandler (Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
