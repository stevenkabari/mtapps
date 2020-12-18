package com.example.mtapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

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

    private String CREATE_IN_TABLE = "CREATE TABLE "+ INCOME + "(" + NAME + " TEXT," + DESC + " TEXT, " + DATE +" TEXT," + COST + " TEXT," + NUMBER + " TEXT," + TOTAL + "  INTEGER,"+ " TEXT"+")";

    private String CREATE_EX_TABLE = "CREATE TABLE "+ EXPENSES + "(" + NAME + " TEXT," + DESC + " TEXT, " + DATE +" TEXT," + COST + " TEXT," + NUMBER + " TEXT," + TOTAL + " INTEGER,"+ " TEXT"+")";

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

    public void saveIncome(String name,String description,String date, String cost , String number,String total){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DBaseHandler.NAME,name);
        values.put(DBaseHandler.DESC,description);
        values.put(DBaseHandler.DATE,date);
        values.put(DBaseHandler.COST,cost);
        values.put(DBaseHandler.NUMBER, number);
        values.put(DBaseHandler.TOTAL,total);

        long status=db.insert(INCOME,null,values);
        Log.v("insertIncome",status + "");
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

        public int getTotal(){
           int result = 0;

            result = (int) incomeTotal() - expenseTotal();

            return result;
        }

        private int incomeTotal(){
            String In_Query = " SELECT SUM(total) AS totalIn FROM "+INCOME+";";
            int In_total = 0;
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor =  db.rawQuery(In_Query,null);

            if(cursor.moveToFirst()){
                In_total = (int)cursor.getInt(cursor.getColumnIndex("totalIn"));
            }
            cursor.close();

            return In_total;
        }
        private int expenseTotal(){
            String Ex_Query = " SELECT SUM(total) AS totalEx FROM "+EXPENSES+";";
            int Ex_total = 0;
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor =  db.rawQuery(Ex_Query,null);

            if(cursor.moveToFirst()){
                Ex_total = (int)cursor.getInt(cursor.getColumnIndex("totalEx"));
            }
            cursor.close();

            return Ex_total;
        }

    public ArrayList<HashMap<String, String>> GetIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> incomeList = new ArrayList<>();
        String query = "SELECT * FROM "+ INCOME;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> income = new HashMap<>();
            income.put("text",cursor.getString(cursor.getColumnIndex("TEXT")));
            income.put("name",cursor.getString(cursor.getColumnIndex(NAME)));
            income.put("date",cursor.getString(cursor.getColumnIndex(DATE)));
            income.put("description",cursor.getString(cursor.getColumnIndex(DESC)));
            income.put("number",cursor.getString(cursor.getColumnIndex(NUMBER)));
            income.put("cost",cursor.getString(cursor.getColumnIndex(COST)));
            income.put("total",cursor.getString(cursor.getColumnIndex(TOTAL)));
            incomeList.add(income);
        }
        return  incomeList;
    }
    public ArrayList<HashMap<String, String>> GetExpense(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> expenseList = new ArrayList<>();
        String query = "SELECT * FROM "+ EXPENSES;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> expense = new HashMap<>();
            expense.put("name",cursor.getString(cursor.getColumnIndex(NAME)));
            expense.put("date",cursor.getString(cursor.getColumnIndex(DATE)));
            expense.put("description",cursor.getString(cursor.getColumnIndex(DESC)));
            expense.put("number",cursor.getString(cursor.getColumnIndex(NUMBER)));
            expense.put("cost",cursor.getString(cursor.getColumnIndex(COST)));
            expense.put("total",cursor.getString(cursor.getColumnIndex(TOTAL)));
            expenseList.add(expense);
            Log.v("ad","do I even");
        }
        return  expenseList;
    }


}
