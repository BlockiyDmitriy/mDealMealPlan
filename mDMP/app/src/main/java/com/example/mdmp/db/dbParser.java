package com.example.mdmp.db;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class dbParser {
    private ArrayList<db> products;

    public dbParser(){
        products = new ArrayList<>();
    }

    public ArrayList<db> getProducts(){
        return  products;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        db currentdb = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("product".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentdb = new db();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("product".equalsIgnoreCase(tagName)){
                                products.add(currentdb);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)){
                                currentdb.setCategory(textValue);
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
