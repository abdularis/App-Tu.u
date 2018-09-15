package com.aar.app.apptuu.features.searchbyvoice;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aar.app.apptuu.data.room.AppDatabase;
import com.aar.app.apptuu.data.room.VideoItemDao;
import com.aar.app.apptuu.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VoiceSearchViewModel extends AndroidViewModel {

    private VideoItemDao mVideoItemDao;
    private MutableLiveData<List<VideoItem>> mOnSearchResult;
    private List<String> mLastKeywords;

    public VoiceSearchViewModel(@NonNull Application application) {
        super(application);
        mVideoItemDao = AppDatabase.getInstance(application).getVideoItemDao();
        mOnSearchResult = new MutableLiveData<>();
        mLastKeywords = new ArrayList<>();
    }

    @SuppressLint("CheckResult")
    public void search(List<String> keywords) {
        mLastKeywords.clear();
        mLastKeywords.addAll(keywords);
        Single
                .create((SingleOnSubscribe<List<VideoItem>>) e -> {
                    List<VideoItem> result = new ArrayList<>();
                    for (String keyword : mLastKeywords) {
                        List<VideoItem> videoItems = mVideoItemDao.searchVideoItemsSync(keyword);
                        if (videoItems.size() > 0) {
                            result.add(videoItems.get(0));
                        }
                    }

                    e.onSuccess(result);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mOnSearchResult::setValue);
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> search(new ArrayList<>(mLastKeywords)))
                .subscribe();
    }

    public LiveData<List<VideoItem>> getOnSearchResult() {
        return mOnSearchResult;
    }
}
