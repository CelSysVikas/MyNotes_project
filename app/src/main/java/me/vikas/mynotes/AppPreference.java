package me.vikas.mynotes;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static final String MY_PREFERENCE = "MyAppPreferences";
    private static final String LANG = "lang";
    private static final String THEME = "theme";
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // Private constructor to enforce Singleton pattern
    private AppPreference(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private static AppPreference preferenceInstance;

    public static synchronized AppPreference getInstance(Context context) {
        if (preferenceInstance == null) {
            preferenceInstance = new AppPreference(context);
        }
        return preferenceInstance;
    }

    public void setThemePreference(Boolean theme) {
        editor.putBoolean(THEME, theme);
        editor.apply();
    }

    public Boolean getThemePreference() {
        return preferences.getBoolean(THEME, true); // Default theme is "light"
    }

    public void setLocalePreference(String lang) {
        editor.putString(LANG, lang);
        editor.apply();
    }

    public String getLocalePreference() {
        return preferences.getString(LANG, "en"); // Default language is "en"
    }
}

