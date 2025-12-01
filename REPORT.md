# Project Report

## Design Decisions

### Architecture
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
- How do you represent game state? (arrays, maps, objects?)
- Why did you choose these structures?

### Algorithms
- Key algorithms implemented (e.g., collision detection, word validation)
- Complexity analysis (Big-O) if relevant

## Challenges Faced
1. **Challenge 1:** Description
   - **Solution:** How you overcame it
   
2. **Challenge 2:** Description
   - **Solution:** How you overcame it

## What We Learned
- OOP concepts reinforced
- Design patterns discovered
- Testing insights

## If We Had More Time
- Feature we'd add
- Refactoring we'd do
- Performance improvements
