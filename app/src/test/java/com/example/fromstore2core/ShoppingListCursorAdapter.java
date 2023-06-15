package com.example.fromstore2core;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Toast;
import c.R;

public class ShoppingListCursorAdapter extends CursorAdapter {
    private Context context;

    public ShoppingListCursorAdapter(Context context) {
        super(context, null, 0);
        this.context = context;
    }

    @Override
    public GroceryList getItem(int position) {
        try {
            Cursor c = getCursor();
            c.moveToPosition(position);
            return GroceryListDAO.returnClassInstance(context, c);
        } catch (Exception e) {
            return null;
        }
    }

    public void refreshCursorAdapter() {
        try {
            changeCursor(GroceryListDAO.selectAll(context));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        notifyDataSetChanged();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GroceryList groceryList = getItem(cursor.getPosition());

    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        LayoutInflater inflater = (LayoutInflater) arg0.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.support_simple_spinner_dropdown_item, null);
    }

}

