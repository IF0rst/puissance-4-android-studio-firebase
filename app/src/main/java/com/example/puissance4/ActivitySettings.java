package com.example.puissance4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySettings extends AppCompatActivity {
    private CheckBox DarkModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsHelper Settings = new SettingsHelper(getApplicationContext());
        Settings.ApplyOptions(findViewById(R.id.main));

        DarkModeEnabled = findViewById(R.id.DarkModeOption);
        DarkModeEnabled.setChecked(Settings.getBoolean("DarkMode",false));

        DarkModeEnabled.setOnClickListener(v->{
            Settings.setBoolean("DarkMode",DarkModeEnabled.isChecked());
        });
    }
}