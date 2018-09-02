package com.aar.app.apptuu.videolist;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.aar.app.apptuu.PlayerPreference;
import com.aar.app.apptuu.R;
import com.aar.app.apptuu.easyadapter.MultiTypeAdapter;
import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoListActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_NAME = "category_name";

    @BindView(R.id.rvVideos)
    RecyclerView mRecyclerView;
    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.textCategory)
    TextView mTvCategory;
    @BindView(R.id.textDesc)
    TextView mTvDesc;
    @BindView(R.id.ivRepeat)
    ImageView mIvRepeat;

    private VideoListViewModel mViewModel;
    private MultiTypeAdapter mAdapter;
    private PlayerPreference mPlayerPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        ButterKnife.bind(this);

        mPlayerPreference = PlayerPreference.getInstance(getApplication());
        updatePlayerPrefs();

        mVideoView.setOnCompletionListener(mp -> {
            if (mPlayerPreference.repeatVideo()) {
                VideoItem vi = mViewModel.getCurrentVideoItem();
                if (vi != null) {
                    mVideoView.setVideoURI(Uri.parse(vi.getUri()));
                    mVideoView.start();
                }
            }
        });

        mAdapter = new MultiTypeAdapter();
        mAdapter.addDelegate(
                VideoItem.class,
                R.layout.item_video,
                (model, holder) -> {
                    holder.<TextView>find(R.id.textTitle).setText(model.getWord());
                    ImageView star = holder.find(R.id.ivStar);
                    if (model.isStarred()) {
                        star.setImageResource(R.drawable.ic_star);
                    } else {
                        star.setImageResource(R.drawable.ic_star_border);
                    }

                    star.setOnClickListener(v -> mViewModel.toggleStar(model));
                },
                (model, view) -> {
                    mViewModel.setCurrentVideoItem(model);
                    mVideoView.setVideoURI(Uri.parse(model.getUri()));
                    mVideoView.start();
                }
        );

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mViewModel = ViewModelProviders.of(this).get(VideoListViewModel.class);
        mViewModel.getOnVideoItemLoaded().observe(this, this::onVideoItemLoaded);
        mViewModel.getOnCategoryInfoLoaded().observe(this, this::onCategoryInfoLoaded);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(EXTRA_CATEGORY_ID)) {
                mViewModel.loadVideoItems(extras.getInt(EXTRA_CATEGORY_ID));
            }
        }
    }

    @OnClick(R.id.ivRepeat)
    public void onRepeatClick(View v) {
        if (mPlayerPreference.repeatVideo()) {
            mPlayerPreference.setRepeatVideo(false);
        } else {
            mPlayerPreference.setRepeatVideo(true);
        }

        updatePlayerPrefs();
    }

    private void onCategoryInfoLoaded(CategoryInfo categoryInfo) {
        mTvCategory.setText(categoryInfo.getName());
        mTvDesc.setText(categoryInfo.getItemCount() + " video");
    }

    private void onVideoItemLoaded(List<VideoItem> videoItems) {
        mAdapter.setItems(videoItems);
    }

    private void updatePlayerPrefs() {
        if (mPlayerPreference.repeatVideo()) {
            mIvRepeat.setColorFilter(getResources().getColor(R.color.colorAccent));
        } else {
            mIvRepeat.clearColorFilter();
        }
    }
}
