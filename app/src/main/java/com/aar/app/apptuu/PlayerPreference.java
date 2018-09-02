package com.aar.app.apptuu;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayerPreference {

    private SharedPreferences mPref;

    private static PlayerPreference INSTANCE = null;

    public static PlayerPreference getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PlayerPreference(context);
        }

        return INSTANCE;
    }

    private PlayerPreference(Context context) {
        mPref = context.getSharedPreferences("player_preferences", Context.MODE_PRIVATE);
    }

    public boolean repeatVideo() {
        return mPref.getBoolean("repeat_video", false);
    }

    public void setRepeatVideo(boolean repeat) {
        mPref.edit().putBoolean("repeat_video", repeat).apply();
    }

}
