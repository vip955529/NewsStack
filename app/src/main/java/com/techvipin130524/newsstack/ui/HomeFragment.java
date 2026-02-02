package com.techvipin130524.newsstack.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.techvipin130524.newsstack.R;
import com.techvipin130524.newsstack.adapter.NewsAdapter;
import com.techvipin130524.newsstack.viewmodel.FavoriteViewModel;
import com.techvipin130524.newsstack.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NewsAdapter adapter;
    private HomeViewModel homeViewModel;
    private FavoriteViewModel favoriteViewModel;

    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ID's
        recyclerView = view.findViewById(R.id.recyclerViewNews);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        progressBar.setVisibility(View.VISIBLE);

        // ViewModels FIRST
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        // Adapter ONCE
        adapter = new NewsAdapter(requireContext(), article -> {
            favoriteViewModel.saveBookmark(article);
        });
        recyclerView.setAdapter(adapter);

        favoriteViewModel.getBookmarks()
                .observe(getViewLifecycleOwner(), bookmarks -> {
                    if (bookmarks != null) {
                        adapter.setBookmarkedUrls(bookmarks);
                    }
                });


        setupRecyclerView();
        loadNews();

        return view;


    }

    private void setupRecyclerView() {

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) rv.getLayoutManager();

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition
                        = layoutManager.findFirstVisibleItemPosition();

                boolean isAtBottom
                        = firstVisibleItemPosition + visibleItemCount >= totalItemCount;

                if (!isLoading && !isLastPage && isAtBottom) {
                    loadNews();  // NEXT PAGE CALL
                }
            }
        });
    }

    private void loadNews() {
        isLoading = true;


        homeViewModel.getNews(page).observe(getViewLifecycleOwner(), news -> {

            if (news != null && !news.isEmpty()) {
                adapter.addList(news);
                progressBar.setVisibility(View.GONE);
                page++;                 // increase page ONLY after success
                Log.d("HomeFragment", String.valueOf(page));
            } else {
                isLastPage = true;      // stop pagination
            }

            isLoading = false;
        });
    }
}