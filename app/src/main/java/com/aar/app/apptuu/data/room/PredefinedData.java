package com.aar.app.apptuu.data.room;

import android.content.Context;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;

public final class PredefinedData {

    private static final int ID_ABJ  = 1;
    private static final int ID_ANGK = 2;
    private static final int ID_BND  = 3;
    private static final int ID_BUAH = 4;
    private static final int ID_HWN  = 5;
    private static final int ID_MMK  = 6;
    private static final int ID_ORG  = 7;
    private static final int ID_TRAN = 8;
    private static final int ID_WRN  = 9;


    public static CategoryInfo[] getCategories() {
        return new CategoryInfo[]{
                new CategoryInfo(ID_ABJ, "Abjad", 0, R.drawable.ic_abjad, "#039BE5"),
                new CategoryInfo(ID_ANGK, "Angka", 0, R.drawable.ic_angka, "#8E24AA"),
                new CategoryInfo(ID_BND, "Benda", 0, R.drawable.ic_benda, "#FDD835"),
                new CategoryInfo(ID_BUAH, "Buah", 0, R.drawable.ic_buah, "#D32F2F"),
                new CategoryInfo(ID_HWN, "Hewan", 0, R.drawable.ic_hewan, "#3CB549"),
                new CategoryInfo(ID_MMK, "Mimik", 0, R.drawable.ic_mimik, "#FD1743"),
                new CategoryInfo(ID_ORG, "Orang", 0, R.drawable.ic_orang, "#1564BE"),
                new CategoryInfo(ID_TRAN, "Transportasi", 0, R.drawable.ic_transportasi, "#FF9000"),
                new CategoryInfo(ID_WRN, "Warna", 0, R.drawable.ic_warna, "#10C6F0")
        };
    }

    public static VideoItem[] getVideoItems(Context context) {
        String pkg = context.getPackageName();

        return new VideoItem[] {
                // Abjad
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_intro), "Intro"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_a), "A"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_b), "B"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_c), "C"),

                // angka

        };

    }

    private static String vidUrl(String pkg, int rawRes) {
        return "android.resource://" + pkg + "/" + rawRes;
    }

}
