package com.techvipin130524.newsstack.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.techvipin130524.newsstack.database.AppDatabase;
import com.techvipin130524.newsstack.database.dao.BookmarkDao;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarkRepository {

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




    /*
    private BookmarkDao dao;

    public BookmarkRepository(Context context) {
        dao = AppDatabase.getInstance(context).bookmarkDao();
    }

    public LiveData<List<BookmarkEntity>> getBookmarks() {
        return dao.getAllBookmarks();
    }

    public void insert(BookmarkEntity article) {
        Executors.newSingleThreadExecutor().execute(() -> dao.insert(article));
    }

    public void delete(BookmarkEntity article) {
        Executors.newSingleThreadExecutor().execute(() -> dao.delete(article));
    }

     */
}
