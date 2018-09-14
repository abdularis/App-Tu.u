package com.aar.app.apptuu.features.videolist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aar.app.apptuu.data.room.AppDatabase;
import com.aar.app.apptuu.data.room.CategoryInfoDao;
import com.aar.app.apptuu.data.room.VideoItemDao;
import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VideoListViewModel extends AndroidViewModel {

    private VideoItem mCurrentVideoItem;
    private int mCurrentCategoryId;
    private CategoryInfoDao mCategoryInfoDao;
    private VideoItemDao mVideoItemDao;

    private MutableLiveData<List<VideoItem>> mOnVideoItemLoaded;
    private MutableLiveData<CategoryInfo> mOnCategoryInfoLoaded;

    public VideoListViewModel(@NonNull Application application) {
        super(application);
        mCategoryInfoDao = AppDatabase.getInstance(application).getCategoryInfoDao();
        mVideoItemDao = AppDatabase.getInstance(application).getVideoItemDao();
        mOnVideoItemLoaded = new MutableLiveData<>();
        mOnCategoryInfoLoaded = new MutableLiveData<>();
        mCurrentCategoryId = -1;
    }

    @SuppressLint("CheckResult")
    public void loadVideoItems(int categoryId) {
        mCurrentCategoryId = categoryId;

        mCategoryInfoDao.getCategoryInfo(categoryId)
                .subscribeOn(Schedulers.io())
                .map(categoryInfo -> {
                    categoryInfo.setItemCount(mVideoItemDao.getVideoItemCountByCategory(categoryInfo.getId()));
                    return categoryInfo;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<CategoryInfo, Flowable<List<VideoItem>>>) categoryInfo -> {
                    mOnCategoryInfoLoaded.setValue(categoryInfo);
                    return mVideoItemDao.getVideoItemList(categoryInfo.getId());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mOnVideoItemLoaded::setValue);
    }

    @SuppressLint("CheckResult")
    public void toggleStar(VideoItem videoItem) {
        Completable completable;
        if (videoItem.isStarred()) {
            completable = Completable.fromAction(() -> mVideoItemDao.unstarVideoItem(videoItem.getId()));
        } else {
            completable = Completable.fromAction(() -> mVideoItemDao.starVideoItem(videoItem.getId()));
        }

        completable
                .andThen(mVideoItemDao.getVideoItemList(mCurrentCategoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mOnVideoItemLoaded::setValue);
    }

    public VideoItem getCurrentVideoItem() {
        return mCurrentVideoItem;
    }

    public void setCurrentVideoItem(VideoItem currentVideoItem) {
        mCurrentVideoItem = currentVideoItem;
    }

    public LiveData<List<VideoItem>> getOnVideoItemLoaded() {
        return mOnVideoItemLoaded;
    }

    public LiveData<CategoryInfo> getOnCategoryInfoLoaded() {
        return mOnCategoryInfoLoaded;
    }
}
