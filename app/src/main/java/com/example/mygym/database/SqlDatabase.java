package com.example.mygym.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mygym.database.models.Days;
import com.example.mygym.database.models.WorkOuts;

import java.util.ArrayList;

public class SqlDatabase extends SQLiteOpenHelper {

    public static String TABLE_NAME = "WorkOuts";
    public static String TABLE_NAME_DAYS = "Days";
    public static String TABLE_NAME_SUPER = "Super";

    public SqlDatabase(@Nullable Context context) {
        super(context, "mySql.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //CREAT TABLE
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME +
                "(ida INTEGER PRIMARY KEY , " +
                " day TEXT  , " +
                " type TEXT  , " +
                " typeId TEXT  , " +
                " title TEXT  , " +
                " setType TEXT  , " +
                " setCount TEXT  , " +
                " moveCount TEXT  , " +
                " details TXT)" );

        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME_SUPER +
                "(ida INTEGER PRIMARY KEY , " +
                " idSuper TEXT  , " +
                " day TEXT  , " +
                " title TEXT  , " +
                " setType TEXT  , " +
                " setCount TEXT  , " +
                " moveCount TEXT  , " +
                " details TXT)" );

        //CREAT TABLE
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME_DAYS +
                "(ida INTEGER PRIMARY KEY , " +
                " day TEXT  , " +
                " title TXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //SELECT
    public ArrayList<WorkOuts> getData(){

        ArrayList<WorkOuts> WorkOutsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()){
            do {
                // Passing values


                int id = c.getInt(0);
                String day = c.getString(1);
                String type = c.getString(2);
                String typeId = c.getString(3);
                String title = c.getString(4);
                String setType = c.getString(5);
                String setCount = c.getString(6);
                String moveCount = c.getString(7);
                String details = c.getString(8);
                WorkOuts product = new WorkOuts(id,day,type,typeId,title,setType,setCount,moveCount,details);
                WorkOutsList.add(product);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();

        return WorkOutsList;


    }

    public ArrayList<WorkOuts> getDataSuper(){

        ArrayList<WorkOuts> WorkOutsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_SUPER, null);
        if (c.moveToFirst()){
            do {
                // Passing values


                int id = c.getInt(0);
                String superId = c.getString(1);
                String day = c.getString(2);
                String title = c.getString(3);
                String setType = c.getString(4);
                String setCount = c.getString(5);
                String moveCount = c.getString(6);
                String details = c.getString(7);
                WorkOuts product = new WorkOuts(superId,day,title,setType,setCount,moveCount,details);
                WorkOutsList.add(product);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();

        return WorkOutsList;


    }

    //SELECT
    public ArrayList<Days> getDays(){

        ArrayList<Days> WorkOutsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_DAYS, null);
        if (c.moveToFirst()){
            do {
                // Passing values
                int id = c.getInt(0);
                String day = c.getString(1);
                String title = c.getString(2);
                Days product = new Days(id,day,title);
                WorkOutsList.add(product);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();

        return WorkOutsList;


    }

    //SELECT Find ID
    public boolean getByDay(String day){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select day from WorkOuts where day = ?", new String[]{day+""});
        if (c.moveToFirst()){
            return true;


        }
        c.close();

        return false;
    }

    //DELETE
    public void deleteById(int id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME, "ida=?", new String[]{id+""});

    }

    public void deleteSuperById(String id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME_SUPER, "idSuper=?", new String[]{id+""});

    }

    //DELETE
    public void deleteByDay(String day)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME, "day=?", new String[]{day+""});
        database.delete(TABLE_NAME_DAYS, "day=?", new String[]{day+""});

    }

    //INSERT
    public void InsertWork( String day,String type,String typeId, String title,String setType,String setCount,String moveCount, String descripsion) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("ida", ida);
        contentValues.put("day", day);
        contentValues.put("type", type);
        contentValues.put("typeId", typeId);
        contentValues.put("title", title);
        contentValues.put("setType", setType);
        contentValues.put("setCount", setCount);
        contentValues.put("moveCount", moveCount);
        contentValues.put("details", descripsion);
        database.insert(TABLE_NAME, null, contentValues);

    }

    public void InsertWorkSuper( String ida, String day, String title,String setType,String setCount,String moveCount, String descripsion) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idSuper", ida);
        contentValues.put("day", day);
        contentValues.put("title", title);
        contentValues.put("setType", setType);
        contentValues.put("setCount", setCount);
        contentValues.put("moveCount", moveCount);
        contentValues.put("details", descripsion);
        database.insert(TABLE_NAME_SUPER, null, contentValues);

    }

    //INSERT
    public void InsertDay( String day, String title) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("ida", ida);
        contentValues.put("day", day);
        contentValues.put("title", title);
        database.insert(TABLE_NAME_DAYS, null, contentValues);

    }

    //INSERT
    public void InsertDayWhId( int id, String day, String title) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ida", id);
        contentValues.put("day", day);
        contentValues.put("title", title);
        database.insert(TABLE_NAME_DAYS, null, contentValues);

    }

}
