package com.techvipin130524.newsstack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techvipin130524.newsstack.R;
import com.techvipin130524.newsstack.database.entity.BookmarkEntity;
import com.techvipin130524.newsstack.model.Article;
import com.techvipin130524.newsstack.model.Source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private final Context context;
    private final BookmarkClickListener bookmarkListener;
    private final List<Article> list = new ArrayList<>();
    private final Set<String> bookmarkedUrls = new HashSet<>();


    public NewsAdapter(Context context, BookmarkClickListener bookmarkListener) {
        this.bookmarkListener = bookmarkListener;
        this.context = context;
    }

    // Pagination use
    public void addList(List<Article> news) {
        int start = list.size();
        list.addAll(news);
        notifyItemRangeInserted(start, news.size());
    }
    // Refresh / category change use
    public void replaceList(List<Article> news) {
        list.clear();
        list.addAll(news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_news, parent,false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = list.get(position);

        holder.textTitle.setText(article.getTitle());

        // 🔐 null safety
        Source source = article.getSource();
        holder.textSource.setText(
                source != null ? source.getName() : "Unknown"
        );

        holder.txtDate.setText(article.getPublishedAt());

        Glide.with(context)
                .load(article.getUrlToImage())
                .into(holder.imgNews);

        boolean isBookmarked = bookmarkedUrls.contains(article.getUrl());
        holder.imgBookmark.setImageResource(
                isBookmarked ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_border
        );

        holder.imgBookmark.setOnClickListener(v -> {
            if (bookmarkListener != null) {
                bookmarkListener.onBookmarkClick(article);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView imgNews;
        ImageView imgBookmark;
        TextView textTitle;
        TextView textSource;
        TextView txtDate;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            imgBookmark = itemView.findViewById(R.id.imgBookmark);
            textTitle = itemView.findViewById(R.id.txtTitle);
            textSource = itemView.findViewById(R.id.txtSource);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
    public void submitBookmarkList(List<BookmarkEntity> bookmarks) {
        list.clear();

        for (BookmarkEntity b : bookmarks) {
            Article article = new Article();
            article.setTitle(b.title);
            article.setUrlToImage(b.imageUrl);

            Source source = new Source();
            source.setName(b.source);
            article.setSource(source);
            article.setPublishedAt(b.publishedAt);
            article.setUrl(b.url);


            list.add(article);
        }

        notifyDataSetChanged();
    }
    public void setBookmarkedUrls(List<BookmarkEntity> bookmarks) {
        bookmarkedUrls.clear();
        for (BookmarkEntity b : bookmarks) {
            bookmarkedUrls.add(b.url);
        }
        notifyDataSetChanged();
    }



}
