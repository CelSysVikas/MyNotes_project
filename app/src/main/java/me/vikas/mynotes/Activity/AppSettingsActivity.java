package me.vikas.mynotes.Activity;

import static me.vikas.mynotes.Config.saveLocale;
import static me.vikas.mynotes.Config.saveTheme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

import me.vikas.mynotes.AppPreference;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.databinding.ActivityAppSettingsBinding;

public class AppSettingsActivity extends AppCompatActivity {
    private ActivityAppSettingsBinding dataBinding;
    private RoomHelper helper;
    private AppPreference appPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_app_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding.navigationBar.setNavigationOnClickListener(v -> finish());

        appPrefrence = AppPreference.getInstance(getApplicationContext());
        helper = RoomHelper.getInstance(this);


        initPreference();
        initAppLocale();
        initTheme();
        initDelete();
    }

    private void initPreference() {
        saveLocale(this, appPrefrence.getLocalePreference());
        dataBinding.setCurrentLocale(appPrefrence.getLocalePreference());

        if (appPrefrence.getThemePreference())
            dataBinding.setCurrentTheme("Day");
        else dataBinding.setCurrentTheme("Night");
    }

    private void initDelete() {
        dataBinding.deleteNotes.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(this).setCancelable(false)
                    .setTitle("Delete Your Notes")
                    .setMessage("Are you really want to Delete... Your Notes")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        helper.getDao().deleteData();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }

    private void initTheme() {
        dataBinding.appTheme.setOnClickListener(v -> {

            String[] theme = {"Day", "Night"};
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle("Pick Language");
            dialogBuilder.setItems(theme, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        saveTheme(AppSettingsActivity.this,true);
                        appPrefrence.setThemePreference(true);
                    }
                    if (which == 1) {
                        saveTheme(AppSettingsActivity.this,false);
                        appPrefrence.setThemePreference(false);
                    }
                }
            });
            dialogBuilder.show();

        });

    }

    private void initAppLocale() {
        dataBinding.appLocale.setOnClickListener(v -> {
            String[] language = {"English", "Hindi"};
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle("Pick Language");
            dialogBuilder.setItems(language, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        saveLocale(AppSettingsActivity.this, "en");
                        appPrefrence.setLocalePreference("en");
                        recreate();
                        dataBinding.setCurrentLocale(getString(R.string.english));
                    }
                    if (which == 1) {
                        saveLocale(AppSettingsActivity.this, "hi");
                        appPrefrence.setLocalePreference("hi");
                        recreate();
                        dataBinding.setCurrentLocale(getString(R.string.hindi));
                    }
                }
            });
            dialogBuilder.show();

        });
    }
}