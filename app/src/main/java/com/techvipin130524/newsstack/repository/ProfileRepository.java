package com.techvipin130524.newsstack.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techvipin130524.newsstack.database.AppDatabase;
import com.techvipin130524.newsstack.database.dao.BookmarkDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileRepository {
    private final FirebaseAuth auth;
    private final BookmarkDao bookmarkDao;
    private final ExecutorService executor;

    public ProfileRepository(Application application) {
        auth = FirebaseAuth.getInstance();
        bookmarkDao = AppDatabase
                .getInstance(application)
                .bookmarkDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // ================= USER INFO =================

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public String getUserName() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null && user.getDisplayName() != null
                ? user.getDisplayName()
                : "Guest User";
    }

    public String getUserEmail() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getEmail() : "";
    }

    public String getUserId() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    // ================= BOOKMARKS =================

    public void getBookmarkCount(Callback<Integer> callback) {
        executor.execute(() -> {
            int count = bookmarkDao.getBookmarkCount(getUserId());
            callback.onResult(count);
        });
    }

    public void clearUserBookmarks() {
        executor.execute(() ->
                bookmarkDao.clearUserBookmarks(getUserId())
        );
    }

    // ================= AUTH ACTIONS =================

    public void logout() {
        auth.signOut();
    }

    public void deleteAccount(Callback<Boolean> callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            callback.onResult(false);
            return;
        }

        user.delete()
                .addOnCompleteListener(task ->
                        callback.onResult(task.isSuccessful())
                );
    }

    // ================= SIMPLE CALLBACK =================

    public interface Callback<T> {
        void onResult(T data);
    }
    /*
    private final FirebaseAuth firebaseAuth;
    private final BookmarkDao bookmarkDao;
    private final ExecutorService executorService;

    public ProfileRepository(BookmarkDao bookmarkDao) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.bookmarkDao = bookmarkDao;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // -------- USER --------
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    // -------- BOOKMARK COUNT --------
    public LiveData<Integer> getBookmarkCount(String uid) {
        return bookmarkDao.getBookmarkCount(uid);
    }

    // -------- LOGOUT --------
    public void logout() {
        firebaseAuth.signOut();
    }

    // -------- CLEAR CACHE --------
    public void clearCache() {
        executorService.execute(bookmarkDao::clearAll);
    }

    // -------- DELETE USER DATA --------
    public void deleteUserData(String uid) {
        executorService.execute(() -> bookmarkDao.deleteByUser(uid));
    }

    // -------- DELETE ACCOUNT --------
    public void deleteAccount(AccountCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            callback.onError("User not logged in");
            return;
        }

        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onError("Re-authentication required");
            }
        });
    }

    // -------- CALLBACK --------
    public interface AccountCallback {
        void onSuccess();
        void onError(String message);
    }

     */
}
