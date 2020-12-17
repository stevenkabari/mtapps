package com.example.mtapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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

    private String CREATE_IN_TABLE = "CREATE TABLE "+ INCOME + "(" + NAME + "TEXT," + DESC + "TEXT, " + DATE +"TEXT," + COST + "TEXT," + NUMBER + "TEXT," + TOTAL + "INTEGER,"+ "TEXT"+")";
    private String CREATE_EX_TABLE = "CREATE TABLE "+ EXPENSES + "(" + NAME + "TEXT," + DESC + "TEXT, " + DATE +"TEXT," + COST + "TEXT," + NUMBER + "TEXT," + TOTAL + "INTEGER,"+ "TEXT"+")";

    private String DROP_TABLE = "DROP TABLE IF EXISTS " + INCOME ;
    private String DROP_TABLE_B = "DROP TABLE IF EXISTS " + EXPENSES ;

    public DBaseHandler (Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_IN_TABLE);
        db.execSQL(CREATE_EX_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE); onCreate(db);
        db.execSQL(DROP_TABLE_B); onCreate(db);
    }

    public void saveIncome(String name,String description,String date, String cost , String number,double total){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBaseHandler.NAME,name);
        values.put(DBaseHandler.DESC,description);
        values.put(DBaseHandler.DATE,date);
        values.put(DBaseHandler.COST,cost);
        values.put(DBaseHandler.NUMBER, number);
        values.put(DBaseHandler.TOTAL,total);

        long status=db.insert(INCOME,null,values);
        if(status<=0){
            Toast.makeText(context, "Record saved unsuccessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Record saved successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void saveExpense(String name,String description,String date, String cost , String number,double total){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBaseHandler.NAME,name);
        values.put(DBaseHandler.DESC,description);
        values.put(DBaseHandler.DATE,date);
        values.put(DBaseHandler.COST,cost);
        values.put(DBaseHandler.NUMBER, number);
        values.put(DBaseHandler.TOTAL,total);

        long status=db.insert(EXPENSES,null,values);
        if(status<=0){
            Toast.makeText(context, "Record saved unsuccessfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Record saved successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
