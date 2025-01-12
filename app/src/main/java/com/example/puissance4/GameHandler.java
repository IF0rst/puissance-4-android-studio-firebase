package com.example.puissance4;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameHandler {
    private FirebaseDatabase Db;

    private DatabaseReference GameState;
    private DatabaseReference Player1Tick;
    private DatabaseReference Player2Tick;
    private DatabaseReference Winner;

    private String GameStateData = null;
    private Long Player1TickData = null;
    private Long Player2TickData = null;
    private String WinnerData = null;

    private String CurrentPlayer = null;
    private Thread ConnectionThread = null;

    private static Integer AllowedTime = 2;
    private List<BoardListener> boardListeners = new ArrayList<>();

    private static String[][] Board = new String[6][7];

    public GameHandler() {
        Db = FirebaseDatabase.getInstance();
        Board = new String[6][7];

        GameState = Db.getReference("GameState");
        Player1Tick = Db.getReference("Player1Tick");
        Player2Tick = Db.getReference("Player2Tick");
        Winner = Db.getReference("Winner");

        Log.d("GHandler", "Started");
        InitHandlers();
    }

    public void addBoardListener(BoardListener listener) {
        if (listener != null) {
            boardListeners.add(listener);
        }
    }

    private void notifyBoardUpdate(String[][] board) {
        for (BoardListener listener : boardListeners) {
            listener.onUpdate(board, (CurrentPlayer == null ? "Spectateur" : CurrentPlayer), "None",WinnerData);
        }
    }

    public void Destroy() {
        if (ConnectionThread != null) {
            ConnectionThread.interrupt();
        }
    }

    private void Connect() {
        if (Player1TickData == null || Player2TickData == null || CurrentPlayer != null) {
            return;
        }

        Long Player1Last = System.currentTimeMillis() / 1000 - Player1TickData;
        Long Player2Last = System.currentTimeMillis() / 1000 - Player2TickData;

        if (Player1Last > AllowedTime) {
            CurrentPlayer = "Player1";
            Player1Tick.setValue(System.currentTimeMillis() / 1000);
        } else if (Player2Last > AllowedTime) {
            CurrentPlayer = "Player2";
            Player2Tick.setValue(System.currentTimeMillis() / 1000);
        } else {
            Log.v("GHandler", "Impossible de selectionner un joueur");
            return;
        }

        notifyBoardUpdate(Board);

        ConnectionThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1 * 1000);
                    switch (CurrentPlayer) {
                        case "Player1":
                            Player1Tick.setValue(System.currentTimeMillis() / 1000);
                            break;
                        case "Player2":
                            Player2Tick.setValue(System.currentTimeMillis() / 1000);
                            break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        ConnectionThread.start();
        Log.v("GHandler", "Joueur selectionné: " + CurrentPlayer);
    }

    private void Disconnect(){
        this.Destroy();
    }

    private void SetWinner(String Winner){
        try{
            this.GameState.setValue("{}");
            this.Winner.setValue(Winner);
            Thread.sleep(1 * 1000);
            this.Winner.setValue("None");
        }catch(Exception e){
            Log.v("GHandler","Could not set Winner");
        }
    }

    private boolean CheckWin() {
        for (int y = 0; y < Board.length; y++) {
            for (int x = 0; x < Board[y].length - 3; x++) {
                String player = Board[y][x];
                if (player != null &&
                        player.equals(Board[y][x + 1]) &&
                        player.equals(Board[y][x + 2]) &&
                        player.equals(Board[y][x + 3])) {
                    this.SetWinner(player);
                    return true;
                }
            }
        }
        for (int x = 0; x < Board[0].length; x++) {
            for (int y = 0; y < Board.length - 3; y++) {
                String player = Board[y][x];
                if (player != null &&
                        player.equals(Board[y + 1][x]) &&
                        player.equals(Board[y + 2][x]) &&
                        player.equals(Board[y + 3][x])) {
                    this.SetWinner(player);
                    return true;
                }
            }
        }
        for (int y = 0; y < Board.length - 3; y++) {
            for (int x = 0; x < Board[y].length - 3; x++) {
                String player = Board[y][x];
                if (player != null &&
                        player.equals(Board[y + 1][x + 1]) &&
                        player.equals(Board[y + 2][x + 2]) &&
                        player.equals(Board[y + 3][x + 3])) {
                    this.SetWinner(player);
                    return true;
                }
            }
        }
        for (int y = 3; y < Board.length; y++) {
            for (int x = 0; x < Board[y].length - 3; x++) {
                String player = Board[y][x];
                if (player != null &&
                        player.equals(Board[y - 1][x + 1]) &&
                        player.equals(Board[y - 2][x + 2]) &&
                        player.equals(Board[y - 3][x + 3])) {
                    this.SetWinner(player);
                    return true;
                }
            }
        }
        return false;
    }

    public void PlacePawn(Integer x, Integer y) {
        if (CurrentPlayer == null) {
            return;
        }

        boolean placed = false;

        for (int yLoop = y; yLoop < Board.length; yLoop++) {
            if (Board[yLoop][x] != null) {
                Board[yLoop - 1][x] = CurrentPlayer;
                placed = true;
                break;
            }
        }

        if (!placed) {
            Board[5][x] = CurrentPlayer;
        }

        if (!CheckWin()){
            Gson gson = new Gson();
            GameState.setValue(gson.toJson(Board));
        }
    }

    private void InitHandlers() {
        GameState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameStateData = dataSnapshot.getValue(String.class);

                if (gameStateData != null && gameStateData != "{}") {
                    try{
                        Gson gson = new Gson();
                        String[][] board = gson.fromJson(gameStateData, String[][].class);

                        if (board.length != 0) {
                            Board = board;
                        }
                    }catch(Exception e){}
                } else {
                    Log.e("GHandler", "GameStateData is null");
                }

                notifyBoardUpdate(Board);
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        Player1Tick.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player1TickData = dataSnapshot.getValue(Long.class);
                Connect();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        Player2Tick.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player2TickData = dataSnapshot.getValue(Long.class);
                Connect();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        Winner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                WinnerData = dataSnapshot.getValue(String.class);
                notifyBoardUpdate(Board);
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
