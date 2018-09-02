package com.aar.app.apptuu;

import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aar.app.apptuu.categorylist.CategoryListFragment;
import com.aar.app.apptuu.searchbyvoice.VoiceSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.container) FrameLayout mContainer;
    @BindView(R.id.navigationView) NavigationView mNavView;

    private ActionBarDrawerToggle mDrawerToggle;

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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void initNavDrawer() {
        mNavView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_home:
                showHome();
                break;
            case R.id.item_videos:
                showVideos();
                break;
            case R.id.item_articles:
                showArticles();
                break;
            case R.id.item_about:
                showAbout();
                break;
        }

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void showHome() {
        mToolbar.setTitle(R.string.app_name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, VoiceSearchFragment.newInstance())
                .commit();
    }

    private void showVideos() {
        mToolbar.setTitle("Video Latihan");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CategoryListFragment.newInstance())
                .commit();
    }

    private void showArticles() {

    }

    private void showAbout() {

    }
}
