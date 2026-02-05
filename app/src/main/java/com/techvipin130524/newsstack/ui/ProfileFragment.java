package com.techvipin130524.newsstack.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.techvipin130524.newsstack.MainActivity;
import com.techvipin130524.newsstack.R;
import com.techvipin130524.newsstack.auth.LoginActivity;
import com.techvipin130524.newsstack.utils.PreferenceManager;
import com.techvipin130524.newsstack.utils.ThemeManager;
import com.techvipin130524.newsstack.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    // ===== UI =====
    private ImageView profileImage;
    private TextView userName;
    private TextView userEmail;
    private TextView bookmarkCount;
    private TextView logout;
    private TextView deleteAccount;
    private Switch darkModeSwitch;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Views
        profileImage = view.findViewById(R.id.profileImage);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        bookmarkCount = view.findViewById(R.id.bookmarkCount);
        logout = view.findViewById(R.id.logout);
        deleteAccount = view.findViewById(R.id.deleteAccount);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);

        // Initialise Preference manager
        preferenceManager = new PreferenceManager(requireContext());

        // Initialise ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        observeData();
        setupClicks();
        setupDarkMode();

        return view;
    }

    // ================= OBSERVERS =================

    private void observeData() {

        profileViewModel.getUserName().observe(getViewLifecycleOwner(),
                name -> userName.setText(name)
        );

        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(),
                email -> userEmail.setText(email)
        );

        profileViewModel.getBookmarkCount().observe(getViewLifecycleOwner(),
                count -> bookmarkCount.setText("Saved Articles: " + count)
        );

        profileViewModel.isAccountDeleted().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(requireContext(),
                        "Account deleted successfully",
                        Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
        });
    }

    // ================= CLICKS =================

    private void setupClicks() {

        logout.setOnClickListener(v -> {
            profileViewModel.logout();
            navigateToLogin();
        });

        deleteAccount.setOnClickListener(v -> {
            profileViewModel.deleteAccount();
        });
    }

    // ================= NAVIGATION =================

    private void navigateToLogin() {
        // Replace LoginActivity with your actual login screen
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupDarkMode() {

        // Set initial state
        darkModeSwitch.setChecked(preferenceManager.isDarkModeEnabled());

        // Listen for toggle
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            // Save preference
            preferenceManager.setDarkModeEnabled(isChecked);

            // Apply theme
            ThemeManager.applyTheme(isChecked);

            // Restart activity (IMPORTANT)
            restartApp();
        });
    }

    private void restartApp() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

}