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

    // Insert bookmark (with userId)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(BookmarkEntity bookmark);

    // Get bookmarks for logged-in user
    @Query("SELECT * FROM bookmarks WHERE userId = :userId ORDER BY id DESC")
    LiveData<List<BookmarkEntity>> getAllBookmarks(String userId);

    // Delete single bookmark (user-safe)
    @Query("DELETE FROM bookmarks WHERE url = :url AND userId = :userId")
    void deleteBookmark(String url, String userId);

    // Check if bookmarked by THIS user
    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE url = :url AND userId = :userId)")
    boolean isBookmarked(String url, String userId);

    // Count bookmarks (for Profile)
    @Query("SELECT COUNT(*) FROM bookmarks WHERE userId = :userId")
    int getBookmarkCount(String userId);

    // Clear bookmarks on logout
    @Query("DELETE FROM bookmarks WHERE userId = :userId")
    void clearUserBookmarks(String userId);

/*
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

 */


}