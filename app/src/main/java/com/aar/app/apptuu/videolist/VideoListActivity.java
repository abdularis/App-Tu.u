package com.aar.app.apptuu.videolist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.easyadapter.MultiTypeAdapter;
import com.aar.app.apptuu.easyadapter.SimpleAdapterDelegate;
import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private VideoListViewModel mViewModel;
    private MultiTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        ButterKnife.bind(this);

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

    private void onCategoryInfoLoaded(CategoryInfo categoryInfo) {
        mTvCategory.setText(categoryInfo.getName());
        mTvDesc.setText(categoryInfo.getItemCount() + " video");
    }

    private void onVideoItemLoaded(List<VideoItem> videoItems) {
        mAdapter.setItems(videoItems);
    }
}
