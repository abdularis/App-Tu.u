package com.aar.app.apptuu.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private String mName;
    @Ignore
    private int mItemCount;

    public CategoryInfo() {
        this(-1, "", -1);
    }

    public CategoryInfo(int id, String name, int itemCount) {
        mId = id;
        mName = name;
        mItemCount = itemCount;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public void setItemCount(int itemCount) {
        mItemCount = itemCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CategoryInfo)) return false;

        CategoryInfo o = (CategoryInfo) obj;
        return mId == o.mId && mName.equals(o.mName);
    }
}
