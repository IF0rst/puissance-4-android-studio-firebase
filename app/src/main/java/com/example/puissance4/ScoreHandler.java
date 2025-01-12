package com.example.puissance4;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScoreHandler {

    private static final String FILE_NAME = "scores_holder.json";
    private final File scoreFile;
    private final Gson gson;

    public ScoreHandler(Context context) {
        this.scoreFile = new File(context.getFilesDir(), FILE_NAME);
        this.gson = new Gson();

        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
                saveScores(new ArrayList<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addScore(String score) {
        List<String> scores = getScoresAsList();
        scores.add(score);
        saveScores(scores);
    }

    public String[] getScores() {
        List<String> scores = getScoresAsList();
        return scores.toArray(new String[0]);
    }

    private List<String> getScoresAsList() {
        try (FileReader reader = new FileReader(scoreFile)) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> scores = gson.fromJson(reader, listType);
            return scores != null ? scores : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveScores(List<String> scores) {
        try (FileWriter writer = new FileWriter(scoreFile)) {
            gson.toJson(scores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
