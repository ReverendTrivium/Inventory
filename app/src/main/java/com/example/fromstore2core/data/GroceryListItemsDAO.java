package com.example.fromstore2core.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.fromstore2core.grocerylist.GroceryList;
import com.example.fromstore2core.grocerylist.GroceryListItems;

import java.util.ArrayList;

public class GroceryListItemsDAO {

    public static final String TABLE_NAME = "GROCERYLISTITEMS";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_IDGROCERYLIST = "IDGROCERYLIST";
    public static final String FIELD_PRODUCT = "DESCRIPTION";
    public static final String FIELD_CHECKED = "CHECKED";
    public final static String FIELD_NOTE = "NOTE";

    private static Context context;
    private static int boolean_column_index = 0;


    //Sets context for GroceryListItems DAO
    public GroceryListItemsDAO(Context context) {
        this.context = context;
    }

    public static GroceryListItems insert(Context context, GroceryListItems groceryListItems) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDGROCERYLIST, groceryListItems.getIdGroceryList());
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
        cv.put(FIELD_CHECKED, groceryListItems.isChecked());
        // Add cv.put for the rest of the fields using getters for each variable
        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    //Selects the last item in an instance of the GroceryListItems class
    private static GroceryListItems selectLast(Context context, SQLiteDatabase db) throws Exception {
        try {
            // Add the rest of the fields inside the { }
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT, FIELD_CHECKED }, null, null, null,null, FIELD_ID + " desc");
            if (cursor.moveToNext()) {
                return returnClassInstance(context, cursor);
            }
            cursor.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }

    //Retrieves items from the GroceryListItems ArrayList
    public static ArrayList<GroceryListItems> getListItems(int listID) {
        ArrayList<GroceryListItems> groceryListItems = new ArrayList<>();
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + FIELD_IDGROCERYLIST + " = " + listID, null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            int idShoppingList = res.getInt(1);
            String name = res.getString(2);
            boolean checked = res.getInt(boolean_column_index) > 0;

            GroceryListItems listItems = new GroceryListItems(id, idShoppingList, name, checked);

            groceryListItems.add(listItems);
        }
        return groceryListItems;
    }

    //Deletes item from a GroceryListItems object
    public static boolean deleteItem(GroceryListItems groceryListItem) {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = " + groceryListItem.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    //Deletes item from a GroceryList object
    public static boolean deleteListItems(GroceryList list) {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_IDGROCERYLIST + " = " + list.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    //Updates items in db
    public static void update(Context context, GroceryListItems itemShoppingList) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_PRODUCT, itemShoppingList.getDescription());
        cv.put(FIELD_CHECKED, String.valueOf(itemShoppingList.isChecked()));
        try {
            db.update(TABLE_NAME, cv, FIELD_ID + " = ?", new String[] { String.valueOf(itemShoppingList.getId()) });
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    //Returns an instance of the GroceryListItems class
    @SuppressLint("Range")
    public static GroceryListItems returnClassInstance(Context context, Cursor cursor) {
        return new GroceryListItems(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getInt(cursor.getColumnIndex(FIELD_IDGROCERYLIST)), cursor.getString(cursor.getColumnIndex(FIELD_PRODUCT)), Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(FIELD_CHECKED))));
    }

    //UNUSED FUNCTIONS
    // This will be the insert function to insert into a previous list //
    public static GroceryListItems insert(Context context, SQLiteDatabase db, GroceryListItems groceryListItems) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDGROCERYLIST, groceryListItems.getIdGroceryList());
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
        cv.put(FIELD_CHECKED, groceryListItems.isChecked());
        // Add cv.put for the rest of the fields using getters for each variable
        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static GroceryListItems select(Context context, int idItemShoppingList) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT, FIELD_CHECKED }, FIELD_ID + " = ?", new String[] { String.valueOf(idItemShoppingList) }, null, null, null);

            if (cursor.moveToNext()) {

                return returnClassInstance(context, cursor);
            }

            cursor.close();

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return null;
    }
}

