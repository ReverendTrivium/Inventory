package com.example.fromstore2core.Search;

public class SearchResult {

    public int _id = 17;
    public String name;
    public Double quantity;

    public SearchResult(int id, String name, Double quanity, Double price){
        this._id = id;
        this.name = name;
        this.quantity = quanity;
    }

    public SearchResult(){

    }

    public int getId(){
        return _id;
    }

    public void setId(int id){
        this._id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Double getQuantity(){
        return quantity;
    }

    public void setQuantity(Double quantity){
        this.quantity = quantity;
    }

}
