package com.aar.app.apptuu.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.aar.app.apptuu.model.VideoItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface VideoItemDao {

    @Query("SELECT * FROM video_items")
    Flowable<List<VideoItem>> getVideoItemList();

    @Query("SELECT * FROM video_items WHERE word LIKE :query")
    Single<List<VideoItem>> searchVideoItems(String query);

    @Query("SELECT * FROM video_items WHERE word LIKE :query")
    List<VideoItem> searchVideoItemsSync(String query);

    @Query("SELECT * FROM video_items WHERE category_id=:categoryId")
    Flowable<List<VideoItem>> getVideoItemList(int categoryId);

    @Query("SELECT * FROM video_items WHERE starred=1 ORDER BY starred_date DESC")
    Flowable<List<VideoItem>> getStarredVideoItemList();

    @Query("UPDATE video_items SET starred=1, starred_date=CURRENT_TIMESTAMP WHERE id=:videoItemId")
    void starVideoItem(int videoItemId);

    @Query("UPDATE video_items SET starred=0 WHERE id=:videoItemId")
    void unstarVideoItem(int videoItemId);

    @Query("SELECT COUNT(*) FROM video_items WHERE category_id=:categoryId")
    int getVideoItemCountByCategory(int categoryId);

    @Insert
    void insert(VideoItem videoItem);

    @Insert
    void insertAll(VideoItem... videoItems);
}
