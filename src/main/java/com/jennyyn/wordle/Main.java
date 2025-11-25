package com.jennyyn.wordle;

import com.jennyyn.wordle.controller.GameController;
import com.jennyyn.wordle.model.GameModel;
import com.jennyyn.wordle.model.WordValidator;
import com.jennyyn.wordle.view.GameBoard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // String secret = WordLoader.getRandomWord();


            WordValidator validator = new WordValidator("/words_clean.txt");
            String secret = validator.getRandomWord();
            System.out.println("Secret word: " + secret);

            GameModel model = new GameModel(secret);
            GameBoard view = new GameBoard();


            JFrame frame = new JFrame("Wordle Clone");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            new GameController(model, view, frame);
        });
    }
}

