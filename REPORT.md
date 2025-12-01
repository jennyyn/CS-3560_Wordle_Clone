# Project Report

## Design Decisions

### Architecture
**1. How did you separate model, view, controller?**

**Model:**  
  The Model contains all game logic and data.  
  This includes classes like:
  - `WordValidator` — loads and checks valid words.
  - `GameModel` — stores the secret word, user guesses, guess count, keyboard color states, etc.
  - 'GameState' - an enum that represents the current status of the game (PLAYING, WON, LOST)
  The Model has **no knowledge of the UI**, so it can be tested independently.

**View:**  
  The View is responsible for displaying everything to the user using **Swing components**.  
  It has the class'GameBoard':
  - contains the Wordle grid (`JPanel` with rows/columns),
  - the on-screen keyboard, 
  - color updates for letters.
  The View does **not** process game rules; it only updates visuals based on what the Controller tells it.

**Controller:**  
  The Controller connects the Model and View. It has the class 'GameController' which:
  - listens to user input (keyboard or button clicks),
  - passes guesses to the Model to check them,
  - receives results and tells the View how to update colors and tiles.
  This separation keeps logic and visuals distinct and makes the code cleaner and easier to maintain.

 **2. What interfaces/abstractions did you create?**
 **Abstractions Created**
- **GameModel:** Provides methods to add guesses, get guesses, and track game state while hiding the internal logic of validating guesses and calculating colors.  
- **GameBoard (View):** Provides methods to update the grid and access keyboard buttons, abstracting away the Swing component layout and styling details.  
- **GameController:** Handles user input and coordinates between model and view, abstracting the interaction logic so neither model nor view need to know about each other’s implementation.  note* No explicit Java `interface` was used, but the above classes provide clear abstractions through their public methods.

**3. Why did you choose Swing vs JavaFX?**
I chose Swing because it is easier for smaller GUI projects and because it's already included in standard Java. I am fairly new to implementing GUIs in java so I wanted the procress of creating buttons and labels to be straightforward. I didn't choose JavaFX because it has a steeper learning curve and requires extra setup.

### Data Structures
- **How Game State Is Represented:**  
  - **Lists**: `List<String>` to store guesses in order.  
  - **2D Array of JLabels**: `JLabel[][]` in the view for the visual grid.  
  - **ArrayList of ArrayLists**: `ArrayList<ArrayList<String>>` to store color feedback for each guess.  
  - **Enum**: `GameState` (PLAYING, WON, LOST) to track the overall game status.  

- **Why These Structures Were Chosen:**  
  - **Lists and ArrayLists** allow dynamic addition of guesses as the user plays.  
  - **2D Array** is a simple, fixed structure for the 6x5 grid, making it easy to access and update cells by row/column. 
  - **Enum** provides a clear, type-safe way to represent a finite set of game states.  
  - **ArrayList of ArrayLists** keeps color feedback organized per guess and easily maps to the grid for display.


### Algorithms
**Key algorithms implemented (e.g., collision detection, word validation)**
- **Word Validation**:  
  Uses a `HashSet` in the `WordValidator` class to quickly check if a guessed word exists in the allowed word list. This allows O(1) lookup time per guess.
- **Guess Feedback / Color Assignment**:  
The `GameModel` class compares the guessed word to the secret word and generates a color-coded feedback array for each letter:  
  - Green → correct letter in correct position  
  - Yellow → correct letter in wrong position  
  - Gray → letter not in the word  

## Challenges Faced
1. **Challenge 1:** If a user’s guess contains a letter twice, but that letter only appears once in the secret word, one instance might be marked green and the other yellow, when it should actually be gray.
   - **Solution:** I fixed this by changing how colors are assigned. Instead of just checking if a letter is in the word for yellow, I counted how many times each letter appears. I marked correct positions as green, then remaining occurrences as yellow if unused, and everything else as gray.
   
2. **Challenge 2:** I had trouble trying to make the on-screen keyboard change to the right colors after the word was typed.
   - **Solution:** I fixed this by sending the background color of the cell label to opaque and gray. This then allowed the color change to show

## What We Learned
- **OOP Concepts Reinforced:** Encapsulation, separation of concerns (MVC), and use of enums for fixed states.  
- **Design Patterns Discovered:** Model-View-Controller (MVC) pattern for organizing code
- **Testing Insights:** Learned the importance of unit testing functions like color assignment, word validation, and handling edge cases (e.g., repeated letters).

## If We Had More Time
- **Feature We'd Add:**  hint system for difficult words, score system
- **Refactoring We'd Do:** separate input handling from controller logic, create utility classes for color assignment
- **Performance Improvements:** optimize grid updates, reduce unnecessary repaint calls, and improve word validation efficiency for large word lists, increase words in words file

