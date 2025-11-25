package com.jennyyn.wordle.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class WordValidator {
    private final Set<String> validWords;

    public WordValidator(String wordsFilePath) {
        validWords = new HashSet<>();
        loadWords(wordsFilePath);
    }

    private void loadWords(String wordsFilePath) {
        try {
            // Load from resources folder
            InputStream inputStream = getClass().getResourceAsStream(wordsFilePath);
            if (inputStream == null) {
                System.err.println("Word file not found: " + wordsFilePath);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                validWords.add(line.trim().toUpperCase());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidWord(String guess) {
        return validWords.contains(guess.toUpperCase());
    }

    public String getRandomWord() {
        int size = validWords.size();
        if (size == 0) return "APPLE";  // fallback

        int randIndex = (int) (Math.random() * size);
        return validWords.stream().skip(randIndex).findFirst().orElse("APPLE");
    }

}

