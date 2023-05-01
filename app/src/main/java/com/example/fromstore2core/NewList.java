package com.example.fromstore2core;

import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class NewList extends AppCompatActivity {

    private ArrayList<String> items = new ArrayList<>();
    private static ArrayList<GroceryListItems> groceryItems = new ArrayList<>();
    private static ListView lv_groceryList;
    private static GroceryList grocerylist;

    Boolean isChecked = false;
    public static ItemListViewAdapter listAdapter;
    GroceryListItemsDAO groceryListItemsDAO = new GroceryListItemsDAO(this);

    //Runs when a new NewList object is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            grocerylist = GroceryListDAO.select(this, getIntent().getExtras().getInt("groceryList"));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //displays the name of the grocery list in the header
        this.setTitle(grocerylist.getName() + ": Grocery List");
        //Query the database for all the items associated with the list
        groceryItems = groceryListItemsDAO.getListItems(grocerylist.getId());

        lv_groceryList = findViewById(R.id.lv_groceryList);

        listAdapter = new ItemListViewAdapter(this, groceryItems);
        lv_groceryList.setAdapter(listAdapter);

        lv_groceryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Enables items to be crossed off
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    GroceryListItems groceryListItems = (GroceryListItems) parent.getItemAtPosition(position);
                    isChecked = !groceryListItems.isChecked();
                    groceryListItems.setChecked(isChecked);
                    GroceryListItemsDAO.update(NewList.this, groceryListItems);
                    refreshListView();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NewList.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Allows user to delete item from list
    public static void deleteItem(GroceryListItems item) {
        GroceryListItemsDAO.deleteItem(item);
    }

    //Updates items in list
    public void updateItems() {
        groceryItems = groceryListItemsDAO.getListItems(grocerylist.getId());
        lv_groceryList.setAdapter(listAdapter);
    }

    //Allows user to add item to list
    public void AddItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Item");
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newItem = input.getText().toString();
            items.add(newItem);

            try {
                GroceryListItemsDAO.insert(this, new GroceryListItems(0, grocerylist.getId(), newItem, false));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                GroceryListItemsDAO groceryListItemsDAO = new GroceryListItemsDAO(this);
                groceryItems = groceryListItemsDAO.getListItems(grocerylist.getId());
                listAdapter = new ItemListViewAdapter(this, groceryItems);
                lv_groceryList.setAdapter(listAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast toast = Toast.makeText(getApplicationContext(), "Item Added: " + newItem, Toast.LENGTH_SHORT);
            toast.show();


        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();

    }

    //Enables back button in upper left corner on NewLIst view
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Refreshes the ListView
    private void refreshListView() {
        listAdapter.notifyDataSetChanged();
    }

}

