package org.bandev.buddhaquotes;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import static android.app.UiModeManager.MODE_NIGHT_NO;
import static android.app.UiModeManager.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class  settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", 0);
        Boolean darkmode = sharedPreferences.getBoolean("dark_mode", false);

        if (darkmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            SharedPreferences pref = getContext().getSharedPreferences("Settings", 0);
            final SharedPreferences.Editor editor = pref.edit();
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            PreferenceScreen screen = getPreferenceScreen();
            final ListPreference listPreference = (ListPreference) findPreference("theme");
            if(listPreference.getValue() == null){
                listPreference.setValueIndex(0); //set to index of your deafult value
            }
            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    listPreference.setValue(newValue.toString());
                    String theme = String.valueOf(listPreference.getEntry());
                    Log.d("debug", theme);
                    if (theme.equals("Light")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putBoolean("dark_mode", false);
                        editor.commit();
                    }
                    if (theme.equals("Dark")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("dark_mode", true);
                        editor.commit();
                    }
                    return true;
                }
            });
            final ListPreference textSize = (ListPreference) findPreference("size");
            if(textSize.getValue() == null){
                textSize.setValueIndex(1); //set to index of your default value
            }
            textSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    textSize.setValue(newValue.toString());
                    String size = String.valueOf(textSize.getEntry());
                    Log.d("debug", size);
                    if (size.equals("Small")) {
                        editor.putString("text_size", "sm");
                        editor.commit();
                    }
                    if (size.equals("Medium")) {
                        editor.putString("text_size", "md");
                        editor.commit();
                    }
                    if (size.equals("Large")) {
                        editor.putString("text_size", "lg");
                        editor.commit();
                    }
                    if (size.equals("Extra")) {
                        editor.putString("text_size", "xlg");
                        editor.commit();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // add your animation
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                this.overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}