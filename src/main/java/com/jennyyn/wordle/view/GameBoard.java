package com.jennyyn.wordle.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameBoard extends JPanel {
    private final JLabel[][] gridLabels;
    private final Map<Character, JButton> keyboardButtons;

    public GameBoard() {
        setLayout(new BorderLayout());

        //Grid Panels
        JPanel gridPanel = new JPanel(new GridLayout(6,5,5,5));
        gridLabels = new JLabel[6][5];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setPreferredSize(new Dimension(50, 50));
                cell.setOpaque(true);
                cell.setBackground(Color.LIGHT_GRAY);
                cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                cell.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
                gridLabels[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        //Keyboard Panels
        JPanel keyboardPanel = new JPanel(new GridLayout(3,9,5,5));
        keyboardButtons = new HashMap<>();
        String keys = "QWERTYUIOPASDFGHJKLZXCVBNM";
        for (char key : keys.toCharArray()) {
            JButton button = new JButton(String.valueOf(key));
            button.setFocusable(false);
            button.setFont(new Font("Arial", Font.BOLD, 16));

            // REQUIRED for background color to show
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setContentAreaFilled(true);

            keyboardPanel.add(button);
            keyboardButtons.put(key, button);
        }
        add(keyboardPanel, BorderLayout.SOUTH);
    }

    //Update the grid with a guess and color feedback
    public void updateRow(int row, String guess, String[] colors) {
        for (int col = 0; col < 5; col++) {
            JLabel cell = gridLabels[row][col];
            cell.setText(String.valueOf(guess.charAt(col)));;

            String color = colors[col];

            switch (color) {
                case "GREEN" -> cell.setBackground(Color.GREEN);
                case "YELLOW" -> cell.setBackground(Color.YELLOW);
                case "GRAY" -> cell.setBackground(Color.GRAY);
            }

            // NEW — update keyboard too
            updateKeyboard(guess.charAt(col), color);
        }
    }

    public Map<Character, JButton> getKeyboardButtons() {
        return keyboardButtons;
    }

    public JLabel[][] getGridLabels() {
        return gridLabels;
    }

    public void updateKeyboard(char letter, String color) {
        letter = Character.toUpperCase(letter);
        JButton button = keyboardButtons.get(letter);
        if (button == null) return;

        Color newColor = switch (color) {
            case "GREEN" -> Color.GREEN;
            case "YELLOW" -> Color.YELLOW;
            default -> Color.GRAY;
        };

        // Only upgrade colors: GRAY → YELLOW → GREEN
        Color current = button.getBackground();
        if (current == Color.GREEN) return;          // already best
        if (current == Color.YELLOW && newColor != Color.GREEN) return;

        button.setBackground(newColor);
    }


}
