package com.example.clientwithbottmmenu.dbSave.dbUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelperUser extends SQLiteOpenHelper {
    public static final Integer DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDBUser.db";
    public static final String CONTACTS_TABLE_NAME = "user";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_GENDER = "gender";
    public static final String CONTACTS_COLUMN_AGE = "age";
    public static final String CONTACTS_COLUMN_WEIGHT = "weight";
    public static final String CONTACTS_COLUMN_GROWTH = "growth";
    public static final String CONTACTS_COLUMN_ACTIVITY = "activity";

    public dbHelperUser(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user" +
                "(id integer primary key, gender text, age text, weight text, growth text, activity text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insertUser(String gender, String age, String weight, String growth,
                                 String activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", gender);
        contentValues.put("description", age);
        contentValues.put("protein", weight);
        contentValues.put("fat", growth);
        contentValues.put("carbohydrates", activity);
        db.insert("products", null, contentValues);
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }
}
