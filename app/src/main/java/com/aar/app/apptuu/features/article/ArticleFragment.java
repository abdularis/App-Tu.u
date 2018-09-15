package com.aar.app.apptuu.features.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.aar.app.apptuu.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArticleFragment extends Fragment {

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        String html = "";
        try {
            html = readHtmlFromAssets();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebView webView = v.findViewById(R.id.webView);
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
        return v;
    }

    private String readHtmlFromAssets() throws IOException {
        StringBuilder buf = new StringBuilder();
        InputStream html= getContext().getAssets().open("article.html");
        BufferedReader in=
                new BufferedReader(new InputStreamReader(html, "UTF-8"));
        String str;

        while ((str=in.readLine()) != null) {
            buf.append(str);
        }

        in.close();
        return buf.toString();
    }

}
