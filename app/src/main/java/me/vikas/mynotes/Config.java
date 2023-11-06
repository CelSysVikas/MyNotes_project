package me.vikas.mynotes;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class Config {
    public static final String TABLE_NAME="my_notes";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_CONTENT="content";
    public static final String COLUMN_DATE_TIME="date_time";

    public static void saveLocale(@NonNull Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void saveTheme(Activity activity, Boolean isDay){
        if (isDay) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

}
