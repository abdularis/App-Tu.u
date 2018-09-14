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

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CategoryListViewModel extends AndroidViewModel {

    private CategoryInfoDao mCategoryInfoDao;
    private VideoItemDao mVideoItemDao;

    private MutableLiveData<List<Object>> mOnDataListLoaded;

    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mCategoryInfoDao = appDatabase.getCategoryInfoDao();
        mVideoItemDao = appDatabase.getVideoItemDao();
        mOnDataListLoaded = new MutableLiveData<>();
    }

    @SuppressLint("CheckResult")
    public void loadData() {
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

    private Flowable<List<Object>> getStarredVideoItemFlowable() {
        return mVideoItemDao.getStarredVideoItemList()
                .flatMap(videoItems -> {
                    if (videoItems.isEmpty())
                        return Flowable.just(new ArrayList<>());
                    return Flowable.fromIterable(videoItems)
                            .map((Function<VideoItem, Object>) videoItem -> videoItem)
                            .startWith(new HeaderItem("Video Berbintang"))
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
                            .startWith(new HeaderItem("Kategori"))
                            .toList()
                            .toFlowable();
                });
    }

    public LiveData<List<Object>> getOnDataListLoaded() {
        return mOnDataListLoaded;
    }
}
