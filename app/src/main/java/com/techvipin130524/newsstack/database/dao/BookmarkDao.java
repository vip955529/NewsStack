package com.techvipin130524.newsstack.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.techvipin130524.newsstack.database.entity.BookmarkEntity;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("DELETE FROM bookmarks WHERE url = :url")
    void deleteByUrl(String url);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(BookmarkEntity bookmark);

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    LiveData<List<BookmarkEntity>> getAllBookmarks();

    @Query("DELETE FROM bookmarks WHERE url = :url")
    void deleteBookmark(String url);

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE url = :url)")
    boolean isBookmarked(String url);



    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkEntity article);

    @Delete
    void delete(BookmarkEntity article);

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    LiveData<List<BookmarkEntity>> getAllBookmarks();

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE url = :url)")
    boolean isBookmarked(String url);

         */
}