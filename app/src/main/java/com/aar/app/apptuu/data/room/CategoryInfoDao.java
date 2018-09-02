package com.aar.app.apptuu.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aar.app.apptuu.model.CategoryInfo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CategoryInfoDao {

    @Query("SELECT * FROM categories")
    Flowable<List<CategoryInfo>> getCategoryInfoList();

    @Insert
    void insert(CategoryInfo categoryInfo);

    @Insert
    void insertAll(CategoryInfo... categoryInfos);
}
