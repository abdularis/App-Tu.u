package com.aar.app.apptuu.categorylist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aar.app.apptuu.data.room.AppDatabase;
import com.aar.app.apptuu.data.room.CategoryInfoDao;
import com.aar.app.apptuu.data.room.VideoItemDao;
import com.aar.app.apptuu.model.CategoryInfo;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CategoryListViewModel extends AndroidViewModel {

    private CategoryInfoDao mCategoryInfoDao;
    private VideoItemDao mVideoItemDao;

    private MutableLiveData<List<CategoryInfo>> mOnCategoryInfoLoaded;

    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mCategoryInfoDao = appDatabase.getCategoryInfoDao();
        mVideoItemDao = appDatabase.getVideoItemDao();
        mOnCategoryInfoLoaded = new MutableLiveData<>();
    }

    @SuppressLint("CheckResult")
    public void loadCategories() {
        mCategoryInfoDao.getCategoryInfoList()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<CategoryInfo>, Flowable<List<CategoryInfo>>>) categoryInfos -> {
                    return Flowable.fromIterable(categoryInfos)
                            .map(categoryInfo -> {
                                categoryInfo.setItemCount(
                                        mVideoItemDao.getVideoItemCountByCategory(categoryInfo.getId())
                                );
                                return categoryInfo;
                            })
                            .toList()
                            .toFlowable();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryInfoList -> mOnCategoryInfoLoaded.setValue(categoryInfoList));
    }

    public LiveData<List<CategoryInfo>> getOnCategoryInfoLoaded() {
        return mOnCategoryInfoLoaded;
    }
}
