package me.vikas.mynotes;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefrence {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final String FILE_NAME="myNotes";
    private final int MODE_PRIVATE=0;

    private final String LANG="locale";

    public AppPrefrence(Context context){
        this.context=context;
        preferences=context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        editor=preferences.edit();
    }

    public void themePreference(){

    }

    public void localePreference(String lang){
        editor.putString(LANG,lang);
        editor.apply();
        editor.commit();
    }

    public String getLoalePreference(){
        String lang=preferences.getString(LANG,"en");
        return lang;
    }
}
