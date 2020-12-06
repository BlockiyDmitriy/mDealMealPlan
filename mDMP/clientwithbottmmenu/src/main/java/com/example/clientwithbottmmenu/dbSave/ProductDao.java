package com.example.clientwithbottmmenu.dbSave;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.clientwithbottmmenu.ui.diary.DiaryProduct;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM diaryproduct")
    List<DiaryProduct> getAll();

    @Query("SELECT * FROM diaryproduct WHERE id = :id")
    DiaryProduct getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiaryProduct... product);

    @Update
    void update(DiaryProduct product);

    @Delete
    void delete(DiaryProduct product);
}
