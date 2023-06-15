package com.example.fromstore2core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import c.R;

import java.util.ArrayList;

public class ListsListViewAdapter extends ArrayAdapter<GroceryList> {

    //Creates ListsListViewAdapter instance
    public ListsListViewAdapter(Context context, ArrayList<GroceryList> items) {
        super(context, R.layout.lists_row, items);

    }

    //Gets view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        GroceryList items = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lists_row, parent, false);
        }

        TextView number = (TextView) convertView.findViewById(R.id.list_number);
        number.setText(position + 1 + ".");

        TextView itemName = (TextView) convertView.findViewById(R.id.list_name);
        itemName.setText(items.getName());

        ImageView delete = convertView.findViewById(R.id.list_delete);

        delete.setOnClickListener(new View.OnClickListener() {
                                      //Deletes list when delete button is tapped, displays toast
                                      @Override
                                      public void onClick(View v) {
                                          LoadList.deleteList(items);
                                          LoadList.deleteListItems(items);
                                          remove(items);
                                          notifyDataSetChanged();
                                          Toast toast = Toast.makeText(getContext(), "List Deleted: " + items.getName(), Toast.LENGTH_SHORT);
                                          toast.show();
                                      }
                                  }
        );
        return convertView;
    }
}
