package com.techvipin130524.newsstack.database.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "bookmarks",
        indices = {@Index(value = {"url"}, unique = true)}
)

public class BookmarkEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public String imageUrl;
    public String source;
    public String publishedAt;
    public String url;


    /*
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String imageUrl;
    public String source;
    public String publishedAt;
    public String url;

     */


}
