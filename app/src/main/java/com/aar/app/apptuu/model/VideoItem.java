package com.aar.app.apptuu.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.aar.app.apptuu.data.room.DateConverter;

import java.util.Date;

@Entity(tableName = "video_items")
@TypeConverters(DateConverter.class)
public class VideoItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "category_id")
    private int mCategoryId;
    @ColumnInfo(name = "uri")
    private String mUri;
    @ColumnInfo(name = "word")
    private String mWord;
    @ColumnInfo(name = "starred")
    private boolean mStarred;
    @ColumnInfo(name = "starred_date")
    private Date mStarredDate;

    public VideoItem() {
        this(-1, -1, "", "", false);
    }

    public VideoItem(int id, int categoryId, String uri, String word, boolean starred) {
        mId = id;
        mCategoryId = categoryId;
        mUri = uri;
        mWord = word;
        mStarred = starred;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public boolean isStarred() {
        return mStarred;
    }

    public void setStarred(boolean starred) {
        mStarred = starred;
    }

    public Date getStarredDate() {
        return mStarredDate;
    }

    public void setStarredDate(Date starredDate) {
        mStarredDate = starredDate;
    }
}
