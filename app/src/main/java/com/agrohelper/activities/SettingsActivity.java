package com.agrohelper.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.agrohelper.R;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private RadioGroup languageRadioGroup;
    private RadioGroup unitsRadioGroup;
    private Switch notificationsSwitch;
    private Switch darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        // Set up the toolbar with back button
        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.settings);
        }
        
        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        // Initialize views
        languageRadioGroup = findViewById(R.id.language_radio_group);
        unitsRadioGroup = findViewById(R.id.units_radio_group);
        notificationsSwitch = findViewById(R.id.notifications_switch);
        darkModeSwitch = findViewById(R.id.dark_mode_switch);
        Button saveButton = findViewById(R.id.save_button);
        
        // Load current settings
        loadSettings();
        
        // Set up save button
        saveButton.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        // Load language setting
        String language = sharedPreferences.getString("language", "ru");
        if ("ru".equals(language)) {
            languageRadioGroup.check(R.id.radio_russian);
        } else {
            languageRadioGroup.check(R.id.radio_english);
        }
        
        // Load units setting
        String units = sharedPreferences.getString("units", "metric");
        if ("metric".equals(units)) {
            unitsRadioGroup.check(R.id.radio_metric);
        } else {
            unitsRadioGroup.check(R.id.radio_imperial);
        }
        
        // Load notifications setting
        boolean notifications = sharedPreferences.getBoolean("notifications", true);
        notificationsSwitch.setChecked(notifications);
        
        // Load dark mode setting
        boolean darkMode = sharedPreferences.getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(darkMode);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        // Save language setting
        String language = languageRadioGroup.getCheckedRadioButtonId() == R.id.radio_russian ? "ru" : "en";
        editor.putString("language", language);
        
        // Save units setting
        String units = unitsRadioGroup.getCheckedRadioButtonId() == R.id.radio_metric ? "metric" : "imperial";
        editor.putString("units", units);
        
        // Save notifications setting
        boolean notifications = notificationsSwitch.isChecked();
        editor.putBoolean("notifications", notifications);
        
        // Save dark mode setting
        boolean isDarkMode = darkModeSwitch.isChecked();
        editor.putBoolean("dark_mode", isDarkMode);
        
        // Apply changes
        editor.apply();
        
        // Apply dark mode
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
        
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
