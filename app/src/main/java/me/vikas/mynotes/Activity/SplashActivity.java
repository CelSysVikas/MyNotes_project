package me.vikas.mynotes.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import me.vikas.mynotes.AppPreference;
import me.vikas.mynotes.Config;
import me.vikas.mynotes.R;

public class SplashActivity extends AppCompatActivity {

    private AppPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        preference=AppPreference.getInstance(this);
        Config.saveLocale(this,preference.getLocalePreference());
        Config.saveTheme(this,preference.getThemePreference());


        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },2500);
    }
}