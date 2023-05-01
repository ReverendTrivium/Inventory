package com.example.fromstore2core;

import android.content.Context;
import androidx.annotation.NonNull;

public class GroceryList {

    private int id;
    private String name;

    //Getter and setter for Id variable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Getter and setter for Name variable
    public String getName() {
        return name;
    }

    public void setName(Context context, String name) {
        this.name = (name.isEmpty() ? null : name);
    }

    //Creates GroceryList objects containing Id and Name variables
    public GroceryList(Context context, int id, String name) {
        setId(id);
        setName(context, name);
    }

    //This is what the Array adapter is using to
    //display the name of the item list object
    @NonNull
    @Override
    public String toString() {
        return "" + getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroceryList( int id, String name) {
        setId(id);
        setName(name);
    }

    public GroceryList(Context context) {

    }
}

