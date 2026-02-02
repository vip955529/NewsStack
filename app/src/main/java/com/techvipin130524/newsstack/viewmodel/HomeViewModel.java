package com.techvipin130524.newsstack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.techvipin130524.newsstack.model.Article;
import com.techvipin130524.newsstack.repository.NewsRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final NewsRepository repository;
    private LiveData<List<Article>> newsList;

    public HomeViewModel() {
        repository = new NewsRepository();
    }

    // Get HomeNews from Repository
    public LiveData<List<Article>> getNews(int page) {
        newsList = repository.getNews(page);
        return newsList;
    }
    // Get CategoryNews from Repository
    public LiveData<List<Article>> getCategoryNews(String category, int page) {
        return repository.getCategoryNews(category, page);
    }

}
