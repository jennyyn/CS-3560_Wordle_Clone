package com.jennyyn.wordle;

import com.jennyyn.wordle.controller.GameController;
import com.jennyyn.wordle.model.GameModel;
import com.jennyyn.wordle.view.GameBoard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel("APPLE"); // Example secret word
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

