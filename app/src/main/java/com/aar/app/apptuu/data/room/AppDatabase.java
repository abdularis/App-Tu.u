package com.aar.app.apptuu.data.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;

import java.util.concurrent.Executors;


@Database(entities = {CategoryInfo.class, VideoItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public abstract CategoryInfoDao getCategoryInfoDao();

    public abstract VideoItemDao getVideoItemDao();

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "data.db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor()
                                .execute(() -> {
                                    AppDatabase appDb = getInstance(context);
                                    appDb.getCategoryInfoDao().insertAll(PredefinedData.getCategories());
                                    appDb.getVideoItemDao().insertAll(PredefinedData.getVideoItems(context));
                                });
                    }
                })
                .build();
    }
}
