package com.example.clientwithbottmmenu.dbSave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String PRODUCTS_COLUMN_ID = "id";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_DESCRIPTION = "description";
    public static final String PRODUCTS_COLUMN_PROTEIN = "protein";
    public static final String PRODUCTS_COLUMN_FAT = "fat";
    public static final String PRODUCTS_COLUMN_CARBOHYDRATES = "carbohydrates";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table products " +
                        "(id integer primary key," +
                        " name text,description text,protein text, fat text,carbohydrates text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public boolean insertProduct(String name, String description, String protein,
                                 String fat, String carbohydrates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("protein", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbohydrates", carbohydrates);
        db.insert(
                "products",
                null,
                contentValues);
        return true;
    }

    public boolean updateProducts(Integer id, String name, String description, String protein,
                                  String fat, String carbohydrates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("protein", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbohydrates", carbohydrates);
        db.update(
                "products",
                contentValues,
                "id = ?",
                new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteProduct(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                "products",
                "id = ?",
                new String[]{Integer.toString(id)});
    }


    public ArrayList<String> getAllProducts() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(PRODUCTS_COLUMN_ID)));
        }
        return arrayList;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PRODUCTS_TABLE_NAME);
        return numRows;
    }
}
