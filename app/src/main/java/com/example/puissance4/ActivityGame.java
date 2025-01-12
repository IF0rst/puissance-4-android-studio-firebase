package com.example.puissance4;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class ActivityGame extends AppCompatActivity {
    private GridLayout Layout;
    private GameHandler GHandler;
    private TextView PlayerText;

    private Map<String,ImageView> PhysicalBoard = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        Layout = findViewById(R.id.PawnHolder);
        PlayerText = findViewById(R.id.Player);

        SettingsHelper Settings = new SettingsHelper(getApplicationContext());
        Settings.ApplyOptions(findViewById(R.id.main));

        GHandler = new GameHandler();

        GHandler.addBoardListener(new BoardListener() {
            @Override
            public void onUpdate(String[][] board, String Player, String Turn, String Winner) {
                UpdatePhysicalGrid(board);
                PlayerText.setText("Vous êtes: "+ Player);

                if (Winner != null){
                    if (!Winner.equals("None")) {
                        Intent intent = new Intent(ActivityGame.this, ResultScreen.class);
                        intent.putExtra("IsWinner",Player.equals(Winner));
                        startActivity(intent);
                    }
                }
            }
        });

        // Permet de ne pas avoir un XML lourd
        this.BuildGrid();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GHandler.Destroy();
    }

    private void UpdatePhysicalGrid(String[][] board) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                String key = y + ":" + x;
                ImageView cell = PhysicalBoard.get(key);

                if (cell != null) {
                    String player = board[y][x];
                    if (player == null) {
                        cell.setBackgroundResource(R.drawable.pawn_none);
                    } else if (player.equals("Player1")) {
                        cell.setBackgroundResource(R.drawable.pawn_red);
                    } else if (player.equals("Player2")) {
                        cell.setBackgroundResource(R.drawable.pawn_yellow);
                    }
                }
            }
        }
    }

    private void BuildGrid() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                ImageView ClonedImageView = new ImageView(this);
                ClonedImageView.setBackgroundResource(R.drawable.pawn_none);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        140,
                        140
                );

                params.setMargins(4, 4, 4, 4);
                ClonedImageView.setLayoutParams(params);

                PhysicalBoard.put(y+":"+x,ClonedImageView);

                int finalX = x;
                int finalY = y;

                ClonedImageView.setOnClickListener(v ->{
                    GHandler.PlacePawn(finalX, finalY);
                });

                this.Layout.addView(ClonedImageView);
            }
        }
    }
}
