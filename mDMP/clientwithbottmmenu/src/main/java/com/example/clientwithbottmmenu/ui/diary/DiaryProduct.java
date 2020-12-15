package com.example.clientwithbottmmenu.ui.diary;

public class DiaryProduct {

    private int id;
    private String name;
    private String description;
    private float protein;
    private float fat;
    private float carbohydrates;

    public DiaryProduct(int id, String name, String description, float protein, float fat, float carbohydrates) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }

    public DiaryProduct(String name, String description, float protein, float fat, float carbohydrates) {
        this.name = name;
        this.description = description;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
