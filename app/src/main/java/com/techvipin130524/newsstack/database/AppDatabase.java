package com.techvipin130524.newsstack.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.techvipin130524.newsstack.database.dao.BookmarkDao;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;


@Database(entities = {BookmarkEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "news_db";

    public abstract BookmarkDao bookmarkDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME
            ).build();
        }
        return INSTANCE;
    }
}
