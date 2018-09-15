package com.aar.app.apptuu.features.searchbyvoice;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.easyadapter.MultiTypeAdapter;
import com.aar.app.apptuu.features.videolist.VideoPlayerActivity;
import com.aar.app.apptuu.model.VideoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class VoiceSearchFragment extends Fragment {

    @BindView(R.id.textResult)
    TextView mTvResult;
    @BindView(R.id.rvResult)
    RecyclerView mRvResult;
    @BindView(R.id.textEmpty)
    TextView mTextEmpty;

    private MultiTypeAdapter mAdapter;
    private VoiceSearchViewModel mViewModel;

    public VoiceSearchFragment() {
    }

    public static VoiceSearchFragment newInstance() {
        return new VoiceSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
                    i.putExtra(VideoPlayerActivity.EXTRA_VIDEO_URI, model.getUri());
                    startActivity(i);
                }
        );
        mViewModel = ViewModelProviders.of(this).get(VoiceSearchViewModel.class);
        mViewModel.getOnSearchResult().observe(this, videoItems -> {
            if (videoItems.isEmpty()) {
                mRvResult.setVisibility(View.GONE);
                mTextEmpty.setVisibility(View.VISIBLE);
            } else {
                mRvResult.setVisibility(View.VISIBLE);
                mTextEmpty.setVisibility(View.GONE);
                mAdapter.setItems(videoItems);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_voice_search, container, false);
        ButterKnife.bind(this, v);
        mRvResult.setAdapter(mAdapter);
        mRvResult.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvResult.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        return v;
    }

    @OnLongClick(R.id.btnListen)
    public boolean onListenButtonLongClick() {
        return false;
    }

    @OnClick(R.id.btnListen)
    public void onListenClick() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello");

        try {
            startActivityForResult(i, 123);
        } catch (ActivityNotFoundException e) {
            mTvResult.setText("Sorry, your device is not supported.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mTvResult.setText(res.get(0));
            mViewModel.search(new ArrayList<>(Arrays.asList(res.get(0).split(" "))));
        }
    }
}
