package com.example.puissance4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button PlayButton;
    private Button ScoreButton;
    private Button SettingsButton;
    private Button CreditsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        PlayButton = findViewById(R.id.Play);
        ScoreButton = findViewById(R.id.Score);
        SettingsButton = findViewById(R.id.Settings);
        CreditsButton = findViewById(R.id.Credits);

        ScoreButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivityScoreboard.class);
            startActivity(intent);
        });

        PlayButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivityGame.class);
            startActivity(intent);
        });

        SettingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivitySettings.class);
            startActivity(intent);
        });

        CreditsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActivityCredits.class);
            startActivity(intent);
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this).setTitle("Quitter l'application").setMessage("Êtes-vous sûr de vouloir quitter ?").setPositiveButton("Oui", (dialog, which) -> {
                    finish();
                }).setNegativeButton("Non", (dialog, which) -> {
                    dialog.dismiss();
                }).setCancelable(false).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SettingsHelper Settings = new SettingsHelper(getApplicationContext());
        Settings.ApplyOptions(findViewById(R.id.main));
    }
}