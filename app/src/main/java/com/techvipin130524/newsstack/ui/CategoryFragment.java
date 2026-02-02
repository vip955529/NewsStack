package com.techvipin130524.newsstack.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.techvipin130524.newsstack.R;
import com.techvipin130524.newsstack.adapter.NewsAdapter;
import com.techvipin130524.newsstack.viewmodel.FavoriteViewModel;
import com.techvipin130524.newsstack.viewmodel.HomeViewModel;


public class CategoryFragment extends Fragment {

    private HomeViewModel viewModel;
    FavoriteViewModel favoriteViewModel;
    private NewsAdapter adapter;

    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    ProgressBar progressBarInCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // ID's
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCategory);
        progressBarInCategory = view.findViewById(R.id.progressBarInCategory);

        // set progress bar visible
        progressBarInCategory.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // FIRST create ViewModel
        favoriteViewModel = new ViewModelProvider(this)
                .get(FavoriteViewModel.class);
        viewModel = new ViewModelProvider(this)
                .get(HomeViewModel.class);

        // Adapter
        adapter = new NewsAdapter(requireContext(), article -> {
            if (favoriteViewModel != null) {
                favoriteViewModel.saveBookmark(article);
            }
        });
        recyclerView.setAdapter(adapter);

        favoriteViewModel.getBookmarks()
                .observe(getViewLifecycleOwner(), bookmarks -> {
                    adapter.setBookmarkedUrls(bookmarks);
                });

        // Set default chip
        chipGroup.check(R.id.chip_business);
        loadCategoryNews("business");



        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == View.NO_ID) return;

            Chip chip = group.findViewById(checkedId);
            String category = chip.getTag().toString();
            Log.d("CategoryFragment", category);


            page = 1;
            isLastPage = false;
            adapter.clear();

            loadCategoryNews(category);
        });



        return view;
    }

    private void loadCategoryNews(String category) {
        isLoading = true;
        progressBarInCategory.setVisibility(View.VISIBLE);

        viewModel.getCategoryNews(category, page).observe(getViewLifecycleOwner(), news -> {

            if (news != null && !news.isEmpty()) {
                adapter.replaceList(news);
                progressBarInCategory.setVisibility(View.GONE);
                page++;                 // increase page ONLY after success
                Log.d("HomeFragment", String.valueOf(page));
            } else {
                isLastPage = true;      // stop pagination
            }

            isLoading = false;
        });
    }
}