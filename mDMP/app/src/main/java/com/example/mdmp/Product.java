package com.example.mdmp;

public class Product {

    private String name;
    private String gramms;
    private String protein;
    private String fats;
    private String carbs;
    private String calories;

    public String getName(){
        return name;
    }
    public String getGramms(){
        return gramms;
    }
    public String getProtein(){
        return protein;
    }
    public String getFats(){
        return fats;
    }
    public String getCarbs(){
        return carbs;
    }
    public String getCalories(){
        return calories;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setGramms(String gramms) {
        this.gramms = gramms;
    }
    public void setProtein(String protein) {
        this.protein = protein;
    }
    public void setFats(String fats) {
        this.fats = fats;
    }
    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }
}
