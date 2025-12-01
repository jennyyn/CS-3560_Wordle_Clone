package com.jennyyn.wordle.model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private String secretWord;
    private int attemptsLeft;
    private GameState state;
    private ArrayList<ArrayList<String>> allGuessColors;
    private List<String> guesses;

    public GameModel(String secretWord) {
        this.secretWord = secretWord;
        this.attemptsLeft = 6;
        this.state = GameState.PLAYING;
        this.allGuessColors = new ArrayList<>();
        this.guesses = new ArrayList<>();
    }

    public void addGuess(String guess) {
        if (state != GameState.PLAYING) return;

        // Add this check at the start:
        if (guess.length() != secretWord.length()) {
            // Optionally, throw an exception, or just return without changing attempts
            return;
        }

        guesses.add(guess);
        attemptsLeft--;

        if (guess.equalsIgnoreCase(secretWord)) {
            state = GameState.WON;
        } else if (attemptsLeft == 0) {
            state = GameState.LOST;
        }

        ArrayList<String> colorsForThisGuess = getColorFeedback(guess);
        allGuessColors.add(colorsForThisGuess);
    }


    private ArrayList<String> getColorFeedback(String guess) {
        ArrayList<String> colors = new ArrayList<>(Collections.nCopies(5, "gray"));
        int[] freq = new int[26];  // letter frequencies of secret word

        // Count letters in the secret word
        for (int i = 0; i < 5; i++) {
            char c = secretWord.charAt(i);
            freq[c - 'A']++;
        }

        // PASS 1: Mark greens and reduce frequency
        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);
            if (g == secretWord.charAt(i)) {
                colors.set(i, "green");
                freq[g - 'A']--;  // use up this letter
            }
        }

        // PASS 2: Mark yellows using remaining frequency
        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);

            if (!colors.get(i).equals("green")) { // skip greens
                if (freq[g - 'A'] > 0) {
                    colors.set(i, "yellow");
                    freq[g - 'A']--; // use this letter
                }
            }
        }

        return colors;
    }


    public String getSecretWord() {
        return secretWord;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public ArrayList<ArrayList<String>> getAllGuessColors() {
        return allGuessColors;
    }
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void reset(String newSecretWord) {
        this.secretWord = newSecretWord;
        this.attemptsLeft = 6;
        this.state = GameState.PLAYING;
        this.guesses.clear();
        this.allGuessColors.clear();
    }
}
