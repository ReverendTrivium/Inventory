package com.example.fromstore2core;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fromstore2core.data.DatabaseDAO;
import com.example.fromstore2core.data.GroceryListDAO;
import com.example.fromstore2core.data.GroceryListItemsDAO;
import com.example.fromstore2core.grocerylist.GroceryList;

import java.util.ArrayList;
import java.util.List;

public class LoadList extends AppCompatActivity {
    // Initialize Private Variables
    private ListView saved_lists;
    private List<String> items = new ArrayList<>();
    GroceryList grocerylist;
    private ArrayAdapter listAdapter;
    ArrayList<GroceryList> groceryList;

    GroceryListDAO groceryListDAO = new GroceryListDAO(this);



    //Runs when a new LoadList object is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_lists);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        try {
            grocerylist = (GroceryList) GroceryListDAO.selectAll(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Call DatabaseDAO
        DatabaseDAO databaseDAO = new DatabaseDAO(this);
        groceryList = databaseDAO.getListNames();

        //database
        saved_lists = findViewById(R.id.saved_lists);
        listAdapter = new ListsListViewAdapter(this, groceryList);

        saved_lists.setAdapter(listAdapter);

        saved_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Starts a new intent for a grocery list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryList clickedList = (GroceryList) parent.getItemAtPosition(position);

                Intent intent = new Intent(LoadList.this, NewList.class);
                intent.putExtra("groceryList", clickedList.getId());
                startActivity(intent);
            }
        });
    }

    //Allows app to resume after going on standby
    protected void onResume() {
        refreshListView();
        super.onResume();
        saved_lists.setAdapter(listAdapter);
    }

    //Refreshes the ListView
    private void refreshListView() {
        listAdapter.notifyDataSetChanged();
    }

    //Allows user to delete an entire list
    public static void deleteList(GroceryList item) {
        GroceryListDAO.deleteList(item);
    }

    //Allows user to delete items from a list
    public static void deleteListItems(GroceryList item) {
        GroceryListItemsDAO.deleteListItems(item);
    }

    //Enables back button in upper left corner on LoadLIst view
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

