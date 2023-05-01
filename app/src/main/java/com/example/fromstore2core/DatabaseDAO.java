package com.example.fromstore2core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;

public class DatabaseDAO extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "GROCERYLIST";
    public static final String DATABASE_NAME = "GroceryList";
    private Context context;


    //Creates DatabaseDAO instance
    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    //Creates entry in SQLite database when instance of class is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT);");
        db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT, CHECKED VARCHAR(1));");
    }

    //Upgrades DAO
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT);");
            db.execSQL("INSERT INTO GROCERYLIST(_id,NAME) SELECT ID,NAME FROM GROCERYLIST;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLIST;");

            db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT, CHECKED VARCHAR(1));");
            db.execSQL("INSERT INTO GROCERYLISTITEMS(_id,IDSHOPPINGLIST,DESCRIPTION,CHECKED) SELECT ID,IDGROCERYLIST,DESCRIPTION, CHECKED FROM GROCERYLISTITEMS;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLISTITEMS;");

            onCreate(db);
            db.execSQL("INSERT INTO GROCERYLIST(_id,NAME) SELECT _id,NAME FROM GROCERYLISTX;");
            db.execSQL("INSERT INTO GROCERYLISTITEMS(_id,IDSHOPPINGLIST,DESCRIPTION, CHECKED) SELECT _id,IDSHOPPINGLIST,DESCRIPTION, CHECKED FROM GROCERYLISTITEMSX;");
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
        }
    }

    //Gets list names in GroceryList ArrayList
    public ArrayList<GroceryList> getListNames() {
        ArrayList<GroceryList> groceryLists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);

            GroceryList newList = new GroceryList(id, name);
            groceryLists.add(newList);
        }
        return groceryLists;
    }
}

