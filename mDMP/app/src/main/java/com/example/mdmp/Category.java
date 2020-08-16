package com.example.mdmp;

import java.util.ArrayList;

public class Category {

    private String name;
    private String description;

    public ArrayList<Product> products;

    public Category(){
        products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
