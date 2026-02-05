package com.techvipin130524.newsstack.utils;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private ThemeManager() {
        // Prevent instantiation
    }

    public static void applyTheme(boolean isDarkModeEnabled) {
        AppCompatDelegate.setDefaultNightMode(
                isDarkModeEnabled
                        ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO
        );
    }
}
