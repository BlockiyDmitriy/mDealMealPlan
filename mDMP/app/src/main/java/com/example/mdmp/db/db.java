package com.example.mdmp.db;

import com.example.mdmp.Category;
import com.example.mdmp.Product;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class db {
// Reads the last product
    public ArrayList<Category> categorys;

    public db(){
        categorys = new ArrayList<>();
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Product currentProduct = null;
        Category currentCategory = new Category();
        boolean inEntry = false;
        String textValue = "";
        int i = 0;

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();

                switch (eventType){
                    // если это открывающий тег элемента product, то создаем новый объект Product
                    // и устанавливаем, что мы находимся внутри элемента product
                    case XmlPullParser.START_TAG:
                       if("Category".equalsIgnoreCase(tagName)){
                           String attributeValueName = xpp.getAttributeValue(null, "name");
                           String attributeValueDescription = xpp.getAttributeValue(null, "description");
                           currentCategory.setName(attributeValueName);
                           currentCategory.setDescription(attributeValueDescription);
                           inEntry = true;
                           currentProduct = new Product();
                        }
                        else if("Product".equalsIgnoreCase(tagName)){
                            inEntry = true;
                        }
                        break;
                    // Если событие TEXT, то считано содержимое элемента,
                    // которое мы можем прочитать с помощью метода getText()
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    // Если закрывающий тег, то все зависит от того, какой элемент прочитан.
                    // Если прочитан элемент product, то добавляем объект Product в коллекцию ArrayList
                    // и сбрываем переменную inEntry, указывая, что мы вышли из элемента product
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("Category".equalsIgnoreCase(tagName)) {
                                categorys.add(i, currentCategory);
                                i++;
                                inEntry = false;
                            }
                            else if("Product".equalsIgnoreCase(tagName)){
                                currentCategory.products.add(currentProduct);
                                inEntry = false;
                            } else if("Name".equalsIgnoreCase(tagName)){
                                currentProduct.setName(textValue);
                            } else if("Gramms".equalsIgnoreCase(tagName)){
                                currentProduct.setGramms(textValue);
                            } else if("Protein".equalsIgnoreCase(tagName)){
                                currentProduct.setProtein(textValue);
                            } else if("Fats".equalsIgnoreCase(tagName)){
                                currentProduct.setFats(textValue);
                            } else if("Carbs".equalsIgnoreCase(tagName)){
                                currentProduct.setCarbs(textValue);
                            } else if("Calories".equalsIgnoreCase(tagName)){
                                currentProduct.setCalories(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}