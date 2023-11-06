package me.vikas.mynotes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

import me.vikas.mynotes.AppPrefrence;
import me.vikas.mynotes.R;
import me.vikas.mynotes.Room.RoomHelper;
import me.vikas.mynotes.databinding.ActivityAppSettingsBinding;

public class AppSettingsActivity extends AppCompatActivity {
    private ActivityAppSettingsBinding dataBinding;
    private RoomHelper helper;
//    private AppPrefrence appPrefrence=new AppPrefrence(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_app_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        dataBinding.navigationBar.setNavigationOnClickListener(v -> finish());

        helper = RoomHelper.getInstance(this);
//        saveLocale(this,appPrefrence.getLoalePreference());

//        if (appPrefrence.getLoalePreference()=="en")
//            dataBinding.setCurrentLocale(getString(R.string.english));
//        if (appPrefrence.getLoalePreference()=="hi")
//            dataBinding.setCurrentLocale(getString(R.string.hindi));



       initAppLocale();

        initTheme();
        initDelete();
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

            String[] theme = {"Day","Night"};
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle("Pick Language");
            dialogBuilder.setItems(theme, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    if (which==1){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
            });
            dialogBuilder.show();

        });

    }

    private void initAppLocale() {
        dataBinding.appLocale.setOnClickListener(v -> {
            String[] language = {"English","Hindi"};
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle("Pick Language");
            dialogBuilder.setItems(language, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        saveLocale(AppSettingsActivity.this, "en");
                        recreate();
                        dataBinding.setCurrentLocale(getString(R.string.english));
                    }
                    if (which==1){
                        saveLocale(AppSettingsActivity.this,"hi");
                        recreate();
                        dataBinding.setCurrentLocale(getString(R.string.hindi));
                    }
                }
            });
            dialogBuilder.show();

        });
    }

    void saveLocale(Activity activity, String langCode){
        Locale locale=new Locale(langCode);
        locale.setDefault(locale);

        Resources resources=activity.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

//        appPrefrence.localePreference(langCode);
    }
}