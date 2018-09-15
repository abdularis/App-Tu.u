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
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_intro), "Abjad"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_a), "A"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_b), "B"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_c), "C"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_d), "D"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_e), "E"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_f), "F"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_g), "G"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_h), "H"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_i), "I"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_j), "J"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_k), "K"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_l), "L"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_m), "M"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_n), "N"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_o), "O"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_p), "P"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_q), "Q"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_r), "R"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_s), "S"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_t), "T"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_u), "U"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_p), "P"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_w), "W"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_x), "X"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_y), "Y"),
                new VideoItem(ID_ABJ, vidUrl(pkg, R.raw.abjad_z), "Z"),

                // Angka
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_intro), "Angka"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_1), "1"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_2), "2"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_3), "3"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_4), "4"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_5), "5"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_6), "6"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_7), "7"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_8), "8"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_9), "9"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_10), "10"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_11), "11"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_12), "12"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_13), "13"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_14), "14"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_15), "15"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_16), "16"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_17), "17"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_18), "18"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_19), "19"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_20), "20"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_100), "100"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_1000), "1000"),
                new VideoItem(ID_ANGK, vidUrl(pkg, R.raw.angka_1000000), "1000000"),


                // Benda
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_intro), "Benda"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_lemari), "Lemari"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_pensil), "Pensil"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_piring), "Piring"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_pulpen), "Pulpen"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_sendok), "Sendok"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_buku), "Buku"),
                new VideoItem(ID_BND, vidUrl(pkg, R.raw.benda_garpu), "Garpu"),


                // Buah
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_intro), "Buah"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_alpukat), "Alpukat"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_anggur), "Anggur"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_apel), "Apel"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_dukuh), "Dukuh"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_jambu), "Jambu"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_kelapa), "Kelapa"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_mangga), "Mangga"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_nanas), "Nanas"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_pisang), "Pisang"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_salak), "Salak"),
                new VideoItem(ID_BUAH, vidUrl(pkg, R.raw.buah_semangka), "Semangka"),


                // Hewan
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_intro), "Hewan"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_anjing), "Anjing"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_beruang), "Beruang"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_burung), "Burung"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_cacing), "Cacing"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_harimau), "Harimau"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_jerapah), "Jerapah"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_kelelawar), "Kelelawar"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_kucing), "Kucing"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_kuda), "Kuda"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_kupu), "Kupu-kupu"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_monyet), "Monyet"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_nyamuk), "Nyamuk"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_singa), "Singa"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_tikus), "Tikus"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_ubur), "Ubur"),
                new VideoItem(ID_HWN, vidUrl(pkg, R.raw.hewan_ular), "Ular"),


                // Mimik
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_intro), "Mimik"),
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_gembira), "Gembira"),
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_marah), "Marah"),
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_sedih), "Sedih"),
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_takut), "Takut"),
                new VideoItem(ID_MMK, vidUrl(pkg, R.raw.mimik_terkejut), "Terkejut"),


                // Orang
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_dia), "Dia"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_intro), "Intro"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_kami), "Kami"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_kamu), "Kamu"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_kita), "Kita"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_mereka), "Mereka"),
                new VideoItem(ID_ORG, vidUrl(pkg, R.raw.orang_saya), "Saya"),


                // Transportasi
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_bis), "Bis"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_intro), "Intro"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_kapal), "Kapal"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_kereta), "Kereta"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_mobil), "Mobil"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_motor), "Motor"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_perahu), "Perahu"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_pesawat), "Pesawat"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_sepeda), "Sepeda"),
                new VideoItem(ID_TRAN, vidUrl(pkg, R.raw.transportasi_truk), "Truk"),


                // Warna
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_intro), "Warna"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_biru), "Biru"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_hijau), "Hijau"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_hitam), "Hitam"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_kuning), "Kuning"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_merah), "Merah"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_orange), "Orange"),
                new VideoItem(ID_WRN, vidUrl(pkg, R.raw.warna_putih), "Putih")
        };
    }

    private static String vidUrl(String pkg, int rawRes) {
        return "android.resource://" + pkg + "/" + rawRes;
    }

}
