package com.example.puissance4;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityScoreboard extends AppCompatActivity {
    private ListView History;
    private static ScoreHandler Score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scoreboard);
        this.History = findViewById(R.id.History);

        SettingsHelper Settings = new SettingsHelper(getApplicationContext());
        Settings.ApplyOptions(findViewById(R.id.main));

        if (Score == null){
            Score = new ScoreHandler(getApplicationContext());
        }

        String[] Items = Score.getScores();
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Items);

        History.setAdapter(Adapter);
    }
}