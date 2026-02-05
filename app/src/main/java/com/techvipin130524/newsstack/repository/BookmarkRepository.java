package com.techvipin130524.newsstack.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.techvipin130524.newsstack.database.AppDatabase;
import com.techvipin130524.newsstack.database.dao.BookmarkDao;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarkRepository {

    private final BookmarkDao bookmarkDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public BookmarkRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        bookmarkDao = db.bookmarkDao();
    }

    private String getUserId() {
        return auth.getCurrentUser() != null
                ? auth.getCurrentUser().getUid()
                : "";
    }

    // Favorites screen
    public LiveData<List<BookmarkEntity>> getBookmarks() {
        return bookmarkDao.getAllBookmarks(getUserId());
    }

    // Toggle bookmark
    public void toggleBookmark(BookmarkEntity bookmark) {
        executor.execute(() -> {
            String userId = getUserId();
            bookmark.userId = userId;

            if (bookmarkDao.isBookmarked(bookmark.url, userId)) {
                bookmarkDao.deleteBookmark(bookmark.url, userId);
            } else {
                bookmarkDao.insertBookmark(bookmark);
            }
        });
    }

    // Check bookmark status
    public boolean isBookmarked(String url) {
        return bookmarkDao.isBookmarked(url, getUserId());
    }

    // Logout cleanup
    public void clearUserBookmarks() {
        executor.execute(() ->
                bookmarkDao.clearUserBookmarks(getUserId())
        );
    }

    /*
    private final BookmarkDao bookmarkDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public BookmarkRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        bookmarkDao = db.bookmarkDao();
    }


    public LiveData<List<BookmarkEntity>> getBookmarks() {
        return bookmarkDao.getAllBookmarks();
    }

    public void delete(String url) {
        executor.execute(() -> bookmarkDao.deleteByUrl(url));
    }

    public void toggleBookmark(BookmarkEntity bookmark) {
        executor.execute(() -> {
            if (bookmarkDao.isBookmarked(bookmark.url) ) {
                bookmarkDao.deleteBookmark(bookmark.url);
            } else {
                bookmarkDao.insertBookmark(bookmark);
            }
        });
    }
    public boolean isBookmarked(String url) {
        return bookmarkDao.isBookmarked(url);
    }

     */

}
