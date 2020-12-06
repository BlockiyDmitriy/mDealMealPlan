package com.example.clientwithbottmmenu.dbSave;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.clientwithbottmmenu.ui.diary.DiaryProduct;

@Database(entities = {DiaryProduct.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
