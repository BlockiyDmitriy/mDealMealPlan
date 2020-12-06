package com.example.clientwithbottmmenu.dbSave;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey
    public long id;
    public String name;
    public String description;
    public float protein;
    public float fat;
    public float carbohydrates;

    public Product(long id, String name, String description, float protein, float fat, float carbohydrates) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }
}
