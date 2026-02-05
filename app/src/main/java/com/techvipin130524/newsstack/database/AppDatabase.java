package com.techvipin130524.newsstack.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.techvipin130524.newsstack.database.dao.BookmarkDao;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;


@Database(entities = {BookmarkEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "news_db";

    public abstract BookmarkDao bookmarkDao();

    // MIGRATION: 1 → 2 (add userId column)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE bookmarks ADD COLUMN userId TEXT"
            );
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME
                    )
                    .addMigrations(MIGRATION_1_2) // use while migrate version
                    .build();
        }
        return INSTANCE;
    }
}
