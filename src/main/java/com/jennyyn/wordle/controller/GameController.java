package com.jennyyn.wordle.controller;

import com.jennyyn.wordle.model.GameModel;
import com.jennyyn.wordle.model.GameState;
import com.jennyyn.wordle.view.GameBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Map;

public class GameController {
    private final GameModel model;
    private final GameBoard view;
    private String currentGuess = "";

    public GameController(GameModel model, GameBoard view) {
        this.model = model;
        this.view = view;

        //Attach listeners to buttons
        for (Map.Entry<Character, JButton> entry : view.getKeyboardButtons().entrySet()) {
            char key = entry.getKey();
            JButton button = entry.getValue(); // corrected 'getValue()'

            button.addActionListener(new ActionListener() { // corrected 'ActionListener'
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleKey(key);
                }
            });
        }


    }

    private void handleKey(char key) {
        if (model.getState() != GameState.PLAYING) return;

        // Build the current guess
        if (key == '\b') { // backspace
            if (!currentGuess.isEmpty()) {
                currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
            }
        } else if (currentGuess.length() < 5) {
            currentGuess += key;
        }

        // Once 5 letters are typed, submit guess
        if (currentGuess.length() == 5) {
            model.addGuess(currentGuess);

            // Convert model's feedback to upper-case for view
            int row = model.getGuesses().size() - 1;
            String[] colors = model.getAllGuessColors().get(row).stream()
                    .map(String::toUpperCase)
                    .toArray(String[]::new);

            view.updateRow(row, currentGuess, colors);

            currentGuess = "";

            // Check game state
            if (model.getState() == GameState.WON) {
                JOptionPane.showMessageDialog(null, "You Win!");
            } else if (model.getState() == GameState.LOST) {
                JOptionPane.showMessageDialog(null, "You Lose! Word was: " + model.getSecretWord());
            }
        }
    }
}
