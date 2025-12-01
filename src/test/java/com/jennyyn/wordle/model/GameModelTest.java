package com.jennyyn.wordle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameModelTest {
    private GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel("APPLE");
    }

    @Test
    void testCorrectGuess() {
        model.addGuess("APPLE");
        assertEquals(GameState.WON, model.getState());
    }

    @Test
    void testIncorrectGuess() {
        model.addGuess("BANJO");
        assertEquals(GameState.PLAYING, model.getState());
        assertEquals(5, model.getAttemptsLeft());
    }

    @Test
    void testRepeatedLettersInGuess() {
        model.addGuess("APPEL"); // 'P' repeated, 'L' out of place
        var colors = model.getAllGuessColors().get(0);
        assertEquals(5, colors.size());
        // Example: check that the first P is green, second P is gray/yellow, etc.
    }

    @Test
    void testInvalidGuessLength() {
        model.addGuess("APP"); // too short
        assertEquals(6, model.getAttemptsLeft()); // attempts shouldn't decrease
    }

    @Test
    void testMaxAttempts() {
        for (int i = 0; i < 6; i++) {
            model.addGuess("WRONG");
        }
        assertEquals(GameState.LOST, model.getState());
    }
}
