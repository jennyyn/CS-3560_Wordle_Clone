package com.jennyyn.wordle.controller;

import com.jennyyn.wordle.model.GameModel;
import com.jennyyn.wordle.model.GameState;
import com.jennyyn.wordle.model.WordValidator;
import com.jennyyn.wordle.view.GameBoard;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class GameController {
    private final GameModel model;
    private final GameBoard view;
    private String currentGuess = "";
    private final WordValidator validator;

    public GameController(GameModel model, GameBoard view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.validator = new WordValidator("/words_clean.txt");

        // Attach listeners to on-screen buttons
        view.getKeyboardButtons().forEach((key, button) ->
                button.addActionListener(e -> handleKey(key))
        );

        // Attach KeyListener to frame for real keyboard typing
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = Character.toUpperCase(e.getKeyChar());
                if (key >= 'A' && key <= 'Z') {
                    handleKey(key);
                } else if (e.getKeyChar() == '\b') { // backspace
                    handleBackspace();
                } else if (e.getKeyChar() == '\n') { // enter key
                    submitGuess();
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    // Handle normal letters
    private void handleKey(char key) {
        if (model.getState() != GameState.PLAYING) return;

        if (currentGuess.length() < 5) {
            currentGuess += key;
            updateCurrentRow();
        }

        if (currentGuess.length() == 5) {
            submitGuess();
        }
    }

    // Handle backspace key
    private void handleBackspace() {
        if (!currentGuess.isEmpty()) {
            currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
            updateCurrentRow();
        }
    }

    // Submit guess once 5 letters are typed
    private void submitGuess() {
        if (currentGuess.length() != 5) return;

        //validate word
        if (!validator.isValidWord(currentGuess)) {
            JOptionPane.showMessageDialog(null, "Invalid word! Try again.");
            updateCurrentRow();
            return;
        }

        //Add guess to model
        model.addGuess(currentGuess);

        // Convert colors to uppercase for view
        int row = model.getGuesses().size() - 1;
        String[] colors = model.getAllGuessColors().get(row).stream()
                .map(String::toUpperCase)
                .toArray(String[]::new);

        view.updateRow(row, currentGuess, colors);
        updateKeyboardColors(currentGuess, colors);
        currentGuess = "";

        // Check game state
        if (model.getState() == GameState.WON) {
            JOptionPane.showMessageDialog(null, "You Win!");
        } else if (model.getState() == GameState.LOST) {
            JOptionPane.showMessageDialog(null, "You Lose! Word was: " + model.getSecretWord());
        }
    }

    // Update the grid row in real-time as user types
    private void updateCurrentRow() {
        int row = model.getGuesses().size(); // current row
        for (int col = 0; col < 5; col++) {
            JLabel cell = view.getGridLabels()[row][col];
            if (col < currentGuess.length()) {
                cell.setText(String.valueOf(currentGuess.charAt(col)));
            } else {
                cell.setText("");
            }
            cell.setBackground(Color.LIGHT_GRAY); // reset colors until submitted
        }
    }

    private void updateKeyboardColors(String guess, String[] colors) {
        for (int i = 0; i < guess.length(); i++) {
            char key = Character.toUpperCase(guess.charAt(i));
            JButton button = view.getKeyboardButtons().get(key);
            if (button != null) {
                switch (colors[i]) {
                    case "GREEN" -> button.setBackground(Color.GREEN);
                    case "YELLOW" -> {
                        // Only upgrade color if it wasn’t green already
                        if (!button.getBackground().equals(Color.GREEN)) {
                            button.setBackground(Color.YELLOW);
                        }
                    }
                    case "GRAY" -> {
                        if (!button.getBackground().equals(Color.GREEN) &&
                                !button.getBackground().equals(Color.YELLOW)) {
                            button.setBackground(Color.GRAY);
                        }
                    }
                }

                button.repaint();
                button.revalidate();
            }
        }
    }

}
