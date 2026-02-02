package com.techvipin130524.newsstack.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.techvipin130524.newsstack.BuildConfig;
import com.techvipin130524.newsstack.model.Article;
import com.techvipin130524.newsstack.model.NewsResponse;
import com.techvipin130524.newsstack.network.NewsApiService;
import com.techvipin130524.newsstack.network.RetrofitClient;
import com.techvipin130524.newsstack.utils.Constants;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsRepository {
    private NewsApiService apiService;

    public NewsRepository() {
        apiService = RetrofitClient.getInstance().create(NewsApiService.class);
    }

    public LiveData<List<Article>> getNews(int page) {

        MutableLiveData<List<Article>> data = new MutableLiveData<>();

        apiService.getTopHeadlines(
                        Constants.COUNTRY,
                        page,
                        Constants.PAGE_SIZE,
                        BuildConfig.NEWS_API_KEY
                ).enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body().getArticles());
                            Log.d("NewsResponse", new Gson().toJson(response.body()).toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });

        return data;
    }

    public LiveData<List<Article>> getCategoryNews(String category, int page) {
        MutableLiveData<List<Article>> data = new MutableLiveData<>();

        apiService.getCategoryNews(
                Constants.COUNTRY,
                category,
                page,
                Constants.PAGE_SIZE,
                BuildConfig.NEWS_API_KEY
        ).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getArticles());
                    Log.d("NewsResponse", new Gson().toJson(response.body()).toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
