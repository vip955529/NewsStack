package com.techvipin130524.newsstack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;
import com.techvipin130524.newsstack.model.Article;
import com.techvipin130524.newsstack.repository.BookmarkRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private final BookmarkRepository repository;
    private final LiveData<List<BookmarkEntity>> bookmarks;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new BookmarkRepository(application);
        bookmarks = repository.getBookmarks(); // already user-filtered
    }

    // Toggle bookmark (add / remove)
    public void saveBookmark(Article article) {

        BookmarkEntity entity = new BookmarkEntity();
        entity.title = article.getTitle();
        entity.description = article.getDescription();
        entity.imageUrl = article.getUrlToImage();
        entity.source = article.getSource().getName();
        entity.publishedAt = article.getPublishedAt();
        entity.url = article.getUrl();
        // ❌ DO NOT set userId here
        // Repository handles Firebase UID internally

        repository.toggleBookmark(entity);
    }

    public boolean isBookmarked(String url) {
        return repository.isBookmarked(url);
    }

    public LiveData<List<BookmarkEntity>> getBookmarks() {
        return bookmarks;
    }

    // Optional: expose clear on logout (used later by Profile)
    public void clearUserBookmarks() {
        repository.clearUserBookmarks();
    }

    /*
    private final BookmarkRepository repository;
    private final LiveData<List<BookmarkEntity>> bookmarks;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new BookmarkRepository(application);
        bookmarks = repository.getBookmarks();
    }

    // THIS IS YOUR METHOD
    public void saveBookmark(Article article) {

        BookmarkEntity entity = new BookmarkEntity();
        // Mapping Article → BookmarkEntity
        entity.title = article.getTitle();
        entity.description = article.getDescription();
        entity.imageUrl = article.getUrlToImage();
        entity.source = article.getSource().getName();
        entity.publishedAt = article.getPublishedAt();
        entity.url = article.getUrl();

        repository.toggleBookmark(entity);
    }
    public boolean isBookmarked(String url) {
        return repository.isBookmarked(url);
    }


    public LiveData<List<BookmarkEntity>> getBookmarks() {
        return bookmarks;
    }

    public void clearUserBookmarks() {
        repository.clearUserBookmarks();
    }

     */



}
