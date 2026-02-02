package com.techvipin130524.newsstack.network;

import com.techvipin130524.newsstack.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<NewsResponse> getCategoryNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}
