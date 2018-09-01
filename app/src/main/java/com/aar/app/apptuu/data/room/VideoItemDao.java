package com.aar.app.apptuu.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aar.app.apptuu.model.VideoItem;

import java.util.List;

@Dao
public interface VideoItemDao {

    @Query("SELECT * FROM video_items")
    List<VideoItem> getVideoItemList();

    @Query("UPDATE video_items SET starred=1")
    void starVideoItem(int videoItemId);

    @Query("UPDATE video_items SET starred=0")
    void unstarVideoItem(int videoItemId);

    @Query("SELECT COUNT(*) FROM video_items WHERE category_id=:categoryId")
    int getVideoItemCountByCategory(int categoryId);

    @Insert
    void insert(VideoItem videoItem);

}
