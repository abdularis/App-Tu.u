package com.aar.app.apptuu.features.searchbyvoice;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aar.app.apptuu.R;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class VoiceSearchFragment extends Fragment {

    @BindView(R.id.textResult)
    TextView mTvResult;

    public VoiceSearchFragment() {
    }

    public static VoiceSearchFragment newInstance() {
        return new VoiceSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_voice_search, container, false);
        ButterKnife.bind(this, v);
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
        }
    }
}
