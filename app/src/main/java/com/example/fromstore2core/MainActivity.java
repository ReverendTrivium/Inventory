package com.example.fromstore2core;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import c.R;


public class MainActivity extends AppCompatActivity {
    GroceryList groceryList;

    //Runs when a new MainActivity object is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.splashScreenTheme);
        setContentView(R.layout.activity_main);
        groceryList = new GroceryList(MainActivity.this);

    }

    //Runs when the New List button is tapped
    public void NewList(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.save));
        alert.setMessage(getString(R.string.title_new));

        final EditText edName = new EditText(this);
        alert.setView(edName);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            //Runs when button is tapped
            public void onClick(DialogInterface dialog, int whichButton) {

                groceryList.setName(MainActivity.this, edName.getText().toString());
                try {
                    startActivity(new Intent(MainActivity.this, NewList.class).putExtra("groceryList", GroceryListDAO.insert(MainActivity.this, groceryList).getId()));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        alert.setNegativeButton(android.R.string.cancel, null);
        alert.show();
    }

    //Loads a list
    public void LoadList(View view) throws Exception {

        Intent intent = new Intent(this, LoadList.class);
        startActivity(intent);

    }

}