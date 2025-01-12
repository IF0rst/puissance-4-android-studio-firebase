package com.example.puissance4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultScreen extends AppCompatActivity {
    private static ScoreHandler Score;
    private TextView Message;
    private ConstraintLayout Background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        this.Message = findViewById(R.id.ResultMessage);
        this.Background = findViewById(R.id.main);

        if (Score == null){
            Score = new ScoreHandler(getApplicationContext());
        }

        Intent intent = getIntent();
        if (intent.getBooleanExtra("IsWinner", false)){
            this.Message.setText("Vous avez gagné!");
            this.Background.setBackgroundColor(Color.parseColor("#00FF00"));
            Score.addScore("Win");
        }else{
            this.Message.setText("Vous avez perdu...");
            this.Background.setBackgroundColor(Color.parseColor("#800000"));
            Score.addScore("Loose");
        }

        new Thread(()->{
            try{
                Thread.sleep(1500);
                Intent MenuIntent = new Intent(ResultScreen.this, MainActivity.class);
                startActivity(MenuIntent);
            }catch (Exception e){}
        }).start();
    }
}