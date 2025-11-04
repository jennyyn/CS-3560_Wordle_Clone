package com.jennyyn.wordle.model;

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
        ArrayList<String> colors = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            char c = guess.charAt(i);
            if (secretWord.charAt(i) == c) {
                colors.add("green");
            } else if (secretWord.contains(String.valueOf(c))) {
                colors.add("yellow");
            } else {
                colors.add("gray");
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
