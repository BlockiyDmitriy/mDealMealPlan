package com.example.clientwithbottmmenu.dbSave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clientwithbottmmenu.ui.diary.DiaryProduct;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "products";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_PROTEIN = "protein";
    public static final String CONTACTS_COLUMN_FAT = "fat";
    public static final String CONTACTS_COLUMN_CARBOHYDRATES = "carbohydrates";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table products " +
                        "(id integer primary key, name text,carbohydrates text,description text," +
                        " protein text,fat text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public boolean insertProduct(String name, String description, String protein, String fat,
                                 String carbohydrates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("protein", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbohydrates", carbohydrates);
        db.insert("products", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateProduct(Integer id, String name, String description, String protein, String fat, String carbohydrates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("protein", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbohydrates", carbohydrates);
        db.update("products", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteProduct(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("products",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<DiaryProduct> getAllProducts() {
        ArrayList<DiaryProduct> array_list = new ArrayList<DiaryProduct>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            DiaryProduct diaryProduct = new DiaryProduct((res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))),
                    (res.getString(res.getColumnIndex(CONTACTS_COLUMN_DESCRIPTION))),
                    (res.getInt(res.getColumnIndex(CONTACTS_COLUMN_PROTEIN))),
                    (res.getInt(res.getColumnIndex(CONTACTS_COLUMN_FAT))),
                    (res.getInt(res.getColumnIndex(CONTACTS_COLUMN_CARBOHYDRATES))));
            array_list.add(diaryProduct);
            res.moveToNext();
        }
        return array_list;
    }
}
