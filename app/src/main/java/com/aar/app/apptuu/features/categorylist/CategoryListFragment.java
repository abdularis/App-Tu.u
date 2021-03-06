package com.aar.app.apptuu.features.categorylist;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.easyadapter.MultiTypeAdapter;
import com.aar.app.apptuu.model.CategoryInfo;
import com.aar.app.apptuu.model.VideoItem;
import com.aar.app.apptuu.features.videolist.VideoListActivity;
import com.aar.app.apptuu.features.videolist.VideoPlayerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryListFragment extends Fragment {

    @BindView(R.id.rvCategory) RecyclerView mRecyclerView;

    private MultiTypeAdapter mAdapter;
    private CategoryListViewModel mViewModel;

    public CategoryListFragment() {
    }

    public static CategoryListFragment newInstance() {
        return new CategoryListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        mAdapter.addDelegate(
                CategoryInfo.class,
                R.layout.item_category,
                (model, holder) -> {
                    holder.<ImageView>find(R.id.imageView).setImageResource(model.getIconResource());
                    holder.find(R.id.frameLayout).setBackgroundColor(Color.parseColor(model.getColor()));
                    holder.<TextView>find(R.id.textCategory).setText(model.getName());
                    holder.<TextView>find(R.id.textDesc).setText(model.getItemCount() + " video");
                },
                (model, view) -> {
                    Intent i = new Intent(getActivity(), VideoListActivity.class);
                    i.putExtra(VideoListActivity.EXTRA_CATEGORY_ID, model.getId());
                    i.putExtra(VideoListActivity.EXTRA_CATEGORY_NAME, model.getName());
                    startActivity(i);
                }
        );
        mAdapter.addDelegate(
                HeaderItem.class,
                R.layout.item_header,
                (model, holder) -> holder.<TextView>find(R.id.textTitle).setText(model.title),
                (model, view) -> {}
        );

        mViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        mViewModel.getOnDataListLoaded().observe(this, mAdapter::setItems);
        mViewModel.getOnVideoItemsFiltered().observe(this, mAdapter::setItems);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                mViewModel.search(newText);
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mViewModel.setMode(CategoryListViewModel.Mode.Search);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mViewModel.setMode(CategoryListViewModel.Mode.Normal);
                mViewModel.loadData();
                return true;
            }
        });
    }
}
