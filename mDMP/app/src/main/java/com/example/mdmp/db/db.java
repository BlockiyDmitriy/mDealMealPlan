package com.example.mdmp.db;

public class db {
    private String сategory;

    public String getCategory(){
        return сategory;
    }

    public void setCategory(String category){
        this.сategory = category;
    }

    public String toString(){
        return  "Category: " + сategory;
    }
}

