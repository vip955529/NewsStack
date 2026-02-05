package com.techvipin130524.newsstack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.techvipin130524.newsstack.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final ProfileRepository repository;

    // ===== LiveData exposed to UI =====
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<Integer> bookmarkCount = new MutableLiveData<>();
    private final MutableLiveData<Boolean> accountDeleted = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
        loadUserInfo();
        loadBookmarkCount();
    }

    // ================= LOADERS =================

    private void loadUserInfo() {
        FirebaseUser user = repository.getCurrentUser();
        if (user != null) {
            userName.setValue(repository.getUserName());
            userEmail.setValue(repository.getUserEmail());
        }
    }

    private void loadBookmarkCount() {
        repository.getBookmarkCount(count ->
                bookmarkCount.postValue(count)
        );
    }

    // ================= EXPOSE LIVE DATA =================

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<Integer> getBookmarkCount() {
        return bookmarkCount;
    }

    public LiveData<Boolean> isAccountDeleted() {
        return accountDeleted;
    }

    // ================= ACTIONS =================

    public void logout() {
        repository.clearUserBookmarks();
        repository.logout();
    }

    public void deleteAccount() {
        repository.clearUserBookmarks();
        repository.deleteAccount(success ->
                accountDeleted.postValue(success)
        );
    }

    // Optional: refresh bookmarks manually
    public void refreshBookmarkCount() {
        loadBookmarkCount();
    }

    /*
    private final ProfileRepository repository;

    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteSuccessLiveData = new MutableLiveData<>();

    public ProfileViewModel(ProfileRepository repository) {
        this.repository = repository;
        loadUser();
    }

    private void loadUser() {
        FirebaseUser user = repository.getCurrentUser();
        if (user != null) {
            userLiveData.setValue(user);
        } else {
            errorLiveData.setValue("User not logged in");
        }
    }

    // -------- GETTERS --------
    public LiveData<FirebaseUser> getUser() {
        return userLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public LiveData<Boolean> getDeleteSuccess() {
        return deleteSuccessLiveData;
    }

    public LiveData<Integer> getBookmarkCount(String uid) {
        return repository.getBookmarkCount(uid);
    }

    // -------- ACTIONS --------
    public void logout() {
        repository.logout();
    }

    public void clearCache() {
        repository.clearCache();
    }

    public void deleteUserData(String uid) {
        repository.deleteUserData(uid);
    }

    public void deleteAccount() {
        repository.deleteAccount(new ProfileRepository.AccountCallback() {
            @Override
            public void onSuccess() {
                deleteSuccessLiveData.postValue(true);
            }

            @Override
            public void onError(String message) {
                errorLiveData.postValue(message);
            }
        });
    }

     */
}
