package com.aar.app.apptuu.categorylist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aar.app.apptuu.R;
import com.aar.app.apptuu.easyadapter.MultiTypeAdapter;
import com.aar.app.apptuu.model.CategoryInfo;

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
        mAdapter = new MultiTypeAdapter();
        mAdapter.addDelegate(
                CategoryInfo.class,
                R.layout.item_category,
                (model, holder) -> {
                    holder.<TextView>find(R.id.textCategory).setText(model.getName());
                    holder.<TextView>find(R.id.textDesc).setText(model.getItemCount() + " video");
                },
                (model, view) -> {}
        );
        mAdapter.addDelegate(
                HeaderItem.class,
                R.layout.item_header,
                (model, holder) -> holder.<TextView>find(R.id.textTitle).setText(model.title),
                (model, view) -> {}
        );

        mViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        mViewModel.getOnCategoryInfoLoaded().observe(this, categoryInfoList -> {
            mAdapter.setItems(categoryInfoList);
            mAdapter.insertAt(0, new HeaderItem("Kategori Video"));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.loadCategories();
    }

}
