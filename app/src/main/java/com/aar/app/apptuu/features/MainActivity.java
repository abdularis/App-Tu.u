package com.aar.app.apptuu.features;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.features.article.ArticleFragment;
import com.aar.app.apptuu.features.categorylist.CategoryListFragment;
import com.aar.app.apptuu.features.searchbyvoice.VoiceSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.container) FrameLayout mContainer;
    @BindView(R.id.navigationView) NavigationView mNavView;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        initNavDrawer();
        showHome();
    }

    @Override
    public void onBackPressed() {
        if (mCurrentMenuId != R.id.item_home) {
            onNavigationItemSelected(mNavView.getMenu().findItem(R.id.item_home));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initNavDrawer() {
        mNavView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        onNavigationItemSelected(mNavView.getMenu().getItem(0));
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_home:
                showHome();
                menuItem.setChecked(true);
                break;
            case R.id.item_videos:
                showVideos();
                menuItem.setChecked(true);
                break;
            case R.id.item_articles:
                showArticles();
                menuItem.setChecked(true);
                break;
            case R.id.item_about:
                showAbout();
                break;
        }

        mDrawerLayout.closeDrawers();
        return true;
    }

    private void showHome() {
        mCurrentMenuId = R.id.item_home;
        mToolbar.setTitle(R.string.app_name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, VoiceSearchFragment.newInstance())
                .commit();
    }

    private void showVideos() {
        mCurrentMenuId = R.id.item_videos;
        mToolbar.setTitle("Video Latihan");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CategoryListFragment.newInstance())
                .commit();
    }

    private void showArticles() {
        mCurrentMenuId = R.id.item_articles;
        mToolbar.setTitle("Artikel");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ArticleFragment.newInstance())
                .commit();
    }

    private void showAbout() {
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }
}
