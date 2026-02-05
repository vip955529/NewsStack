package com.techvipin130524.newsstack.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "newsstack_prefs";
    private static final String KEY_DARK_MODE = "dark_mode_enabled";

    private final SharedPreferences preferences;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /* ---------------- DARK MODE ---------------- */

    public void setDarkModeEnabled(boolean isEnabled) {
        preferences.edit()
                .putBoolean(KEY_DARK_MODE, isEnabled)
                .apply();
    }

    public boolean isDarkModeEnabled() {
        return preferences.getBoolean(KEY_DARK_MODE, false); // default = Light mode
    }

}
