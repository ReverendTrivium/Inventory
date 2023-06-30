package com.example.fromstore2core;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.fromstore2core.grocerylist.GroceryListItems;

import java.util.ArrayList;
public class ItemListViewAdapter extends ArrayAdapter<GroceryListItems> {

    //Creates ItemListViewAdapter instance
    public ItemListViewAdapter(Context context, ArrayList<GroceryListItems> items) {
        super(context, R.layout.item_list_row, items);

    }

    //Gets view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GroceryListItems items = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_row, parent, false);
        }

        TextView number = (TextView) convertView.findViewById(R.id.item_number);
        number.setText(position + 1 + ".");

        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        itemName.setText(items.getDescription());

        itemName.setPaintFlags(items.isChecked() ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.ANTI_ALIAS_FLAG);
        itemName.setTypeface(null, items.isChecked() ? Typeface.ITALIC : Typeface.NORMAL);

        ImageView delete = convertView.findViewById(R.id.item_delete);

        delete.setOnClickListener(new View.OnClickListener() {
                                      //Deletes item when delete button is tapped, displays toast
                                      @Override
                                      public void onClick(View v) {
                                          NewList.deleteItem(items);
                                          remove(items);
                                          notifyDataSetChanged();
                                          Toast toast = Toast.makeText(getContext(), "Item Deleted: " + items.getDescription(), Toast.LENGTH_SHORT);
                                          toast.show();
                                      }
                                  }
        );
        return convertView;
    }

}