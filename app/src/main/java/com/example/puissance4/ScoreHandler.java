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

    // Constructor to initialize the file and Gson instance
    public ScoreHandler(Context context) {
        this.scoreFile = new File(context.getFilesDir(), FILE_NAME);
        this.gson = new Gson();

        // Create the file if it doesn't exist
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
                saveScores(new ArrayList<>()); // Initialize the file with an empty list
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a new score to the list and save it to the file.
     *
     * @param score The score to add.
     */
    public void addScore(String score) {
        List<String> scores = getScoresAsList(); // Get the current list of scores
        scores.add(score); // Add the new score
        saveScores(scores); // Save the updated list
    }

    /**
     * Get the list of scores from the file as a String array.
     *
     * @return A String array of scores.
     */
    public String[] getScores() {
        List<String> scores = getScoresAsList(); // Get the scores as a list
        return scores.toArray(new String[0]); // Convert the list to a String array
    }

    /**
     * Get the list of scores from the file as a List.
     *
     * @return A List of scores.
     */
    private List<String> getScoresAsList() {
        try (FileReader reader = new FileReader(scoreFile)) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> scores = gson.fromJson(reader, listType);
            return scores != null ? scores : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }

    /**
     * Save the list of scores to the file.
     *
     * @param scores The list of scores to save.
     */
    private void saveScores(List<String> scores) {
        try (FileWriter writer = new FileWriter(scoreFile)) {
            gson.toJson(scores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
