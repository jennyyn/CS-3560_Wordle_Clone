# Wordle Clone in Java
A simple Wordle-style game built in Java with Swing. Guess the secret word in 6 attempts!

Video Demo: https://youtu.be/IAyFcDAqug4

## Team Members
1. Jenny Nguyen

## How to Run
1. Clone repository: git clone https://github.com/jennyyn/CS-3560_Wordle_Clone.git
2. Open in Intellij/Eclipse
3. Run `Main.java`

## Features
- **6x5 grid** to display guesses and feedback  
- **Real-time color feedback:**
  - **Green** = correct letter, correct position  
  - **Yellow** = correct letter, wrong position  
  - **Gray** = letter not in word  
- **On-screen clickable keyboard** that updates colors as you guess  
- **Random secret word** chosen from a valid word list  
- **Tracks number of attempts** remaining  
- **Game end detection:** win or lose

## Controls
- **Keyboard typing:** type letters to fill the current row  
- **Backspace:** delete a letter in the current guess  
- **Enter:** submit a guess  
- **Mouse:** click on on-screen keyboard buttons to enter letters  

## Known Issues
- Secret words are always 5 letters; no support for other lengths  
- No hint system or scoring implemented  
- UI may not scale perfectly on very high-resolution screens

## External Libraries
- No external libraries were used

