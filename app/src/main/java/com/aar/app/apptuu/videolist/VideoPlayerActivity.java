package com.aar.app.apptuu.videolist;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.aar.app.apptuu.PlayerPreference;
import com.aar.app.apptuu.R;

public class VideoPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    public static final String EXTRA_VIDEO_URI = "video_uri";
    public static final String EXTRA_PLAY_PROGRESS = "seek_to_ms";

    private VideoView vv;
    private PlayerPreference mPlayerPreference;
    private Uri mVideoUri;
    private int mSeekTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayerPreference = PlayerPreference.getInstance(getApplication());

        vv = new VideoView(this);
        vv.setMediaController(new MediaController(this));
        vv.setOnCompletionListener(this);

        setContentView(vv);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_VIDEO_URI)) {
            mSeekTo = extras.getInt(EXTRA_PLAY_PROGRESS, 0);
            mVideoUri = Uri.parse(extras.getString(EXTRA_VIDEO_URI, ""));

            playVideo();
        } else {
            finish();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayerPreference.repeatVideo()) {
            playVideo();
        } else {
            finish();
        }
    }

    private void playVideo() {
        vv.setVideoURI(mVideoUri);
        vv.seekTo(mSeekTo);
        vv.start();
    }
}
