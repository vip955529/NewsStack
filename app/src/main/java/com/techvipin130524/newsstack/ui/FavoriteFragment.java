package com.techvipin130524.newsstack.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techvipin130524.newsstack.R;
import com.techvipin130524.newsstack.adapter.NewsAdapter;
import com.techvipin130524.newsstack.viewmodel.FavoriteViewModel;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel viewModel;
    private NewsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavorite);
        TextView txtEmpty = view.findViewById(R.id.txtEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(),null);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this)
                .get(FavoriteViewModel.class);

        viewModel.getBookmarks().observe(getViewLifecycleOwner(), list -> {

            if (list == null || list.isEmpty()) {
                txtEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                txtEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                adapter.submitBookmarkList(list);
            }
        });

        return view;
    }
}