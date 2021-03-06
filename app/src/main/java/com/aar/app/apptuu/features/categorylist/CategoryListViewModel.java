package com.aar.app.apptuu.features.categorylist;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CategoryListViewModel extends AndroidViewModel {

    private CategoryInfoDao mCategoryInfoDao;
    private VideoItemDao mVideoItemDao;

    private MutableLiveData<List<VideoItem>> mOnVideoItemsFiltered;
    private MutableLiveData<List<Object>> mOnDataListLoaded;
    private HeaderItem mStarredHeader;
    private HeaderItem mCategoryHeader;
    private Mode mCurrentMode;
    private String mCurrectQuery;

    public enum Mode {
        Search, Normal
    }

    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mCategoryInfoDao = appDatabase.getCategoryInfoDao();
        mVideoItemDao = appDatabase.getVideoItemDao();
        mOnVideoItemsFiltered = new MutableLiveData<>();
        mOnDataListLoaded = new MutableLiveData<>();
        mStarredHeader = new HeaderItem("Video Berbintang");
        mCategoryHeader = new HeaderItem("Kategori");
        mCurrentMode = Mode.Normal;
        mCurrectQuery = "";
    }

    @SuppressLint("CheckResult")
    public void loadData() {
        if (mCurrentMode != Mode.Normal) return;
        Flowable.zip(
                getStarredVideoItemFlowable(),
                getCategoryInfoListFlowable(),
                (BiFunction<List<Object>, List<Object>, List<Object>>) (objects, objects2) -> {
                    ArrayList<Object> res = new ArrayList<>();
                    res.addAll(objects);
                    res.addAll(objects2);
                    return res;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mOnDataListLoaded::setValue);
    }

    @SuppressLint("CheckResult")
    public void search(String query) {
        if (mCurrentMode != Mode.Search) return;
        mCurrectQuery = query;
        mVideoItemDao.searchVideoItems(mCurrectQuery + "%")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mOnVideoItemsFiltered::setValue);
    }

    public void toggleStar(VideoItem model) {
        Completable completable;
        if (model.isStarred()) {
            completable = Completable.fromAction(() -> mVideoItemDao.unstarVideoItem(model.getId()));
        } else {
            completable = Completable.fromAction(() -> mVideoItemDao.starVideoItem(model.getId()));
        }

        completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    if (mCurrentMode == Mode.Normal) {
                        loadData();
                    } else if (mCurrentMode == Mode.Search) {
                        search(mCurrectQuery);
                    }
                })
                .subscribe();
    }

    private Flowable<List<Object>> getStarredVideoItemFlowable() {
        return mVideoItemDao.getStarredVideoItemList()
                .flatMap(videoItems -> {
                    if (videoItems.isEmpty())
                        return Flowable.just(new ArrayList<>());
                    return Flowable.fromIterable(videoItems)
                            .map((Function<VideoItem, Object>) videoItem -> videoItem)
                            .startWith(mStarredHeader)
                            .toList()
                            .toFlowable();
                });
    }

    private Flowable<List<Object>> getCategoryInfoListFlowable() {
        return mCategoryInfoDao.getCategoryInfoList()
                .flatMap(categoryInfoList -> {
                    if (categoryInfoList.isEmpty())
                        return Flowable.just(new ArrayList<>());
                    return Flowable.fromIterable(categoryInfoList)
                            .map(categoryInfo -> {
                                categoryInfo.setItemCount(
                                        mVideoItemDao.getVideoItemCountByCategory(categoryInfo.getId())
                                );
                                return categoryInfo;
                            })
                            .map((Function<CategoryInfo, Object>) categoryInfo -> categoryInfo)
                            .startWith(mCategoryHeader)
                            .toList()
                            .toFlowable();
                });
    }

    public void setMode(Mode mode) {
        mCurrentMode = mode;
    }

    public LiveData<List<Object>> getOnDataListLoaded() {
        return mOnDataListLoaded;
    }

    public LiveData<List<VideoItem>> getOnVideoItemsFiltered() {
        return mOnVideoItemsFiltered;
    }
}
