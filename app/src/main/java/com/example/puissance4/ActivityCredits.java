package com.example.puissance4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCredits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        SettingsHelper Settings = new SettingsHelper(getApplicationContext());
        Settings.ApplyOptions(findViewById(R.id.main));
    }
}