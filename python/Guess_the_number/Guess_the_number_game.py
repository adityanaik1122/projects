# random number guessing game
import random
import tkinter as tk

# Initialize global variables for the game
num = random.randint(1, 100)
attempt = 0

def check_guess():
    """Handles the game logic when the 'Guess' button is clicked."""
    global attempt

    try:
        user_input = int(entry.get())

        if not (1 <= user_input <= 100):
            result_label.config(text="Invalid input! Please enter a number between 1 and 100.")
            return

        attempt += 1

        if user_input == num:
            result_label.config(text=f"Correct! It took you {attempt} attempts.")
            # Disable entry and guess button and show the 'Play Again' button
            guess_button.pack_forget()
            play_again_button.pack(pady=5)
        elif user_input > num:
            result_label.config(text="The number is smaller.")
        else:
            result_label.config(text="The number is bigger.")

    except ValueError:
        result_label.config(text="Invalid input! Please enter a valid number.")

def reset_game():
    """Resets the game to allow the user to play again."""
    global num, attempt
    num = random.randint(1, 100)
    attempt = 0
    entry.delete(0, tk.END)
    result_label.config(text="")
    
    # Re-enable the guess button and entry field
    entry.config(state="normal")
    guess_button.config(state="normal")
    
    # Hide the 'Play Again' button and show the 'Guess' button
    play_again_button.pack_forget()
    guess_button.pack(pady=5)

# --- GUI SETUP ---
window = tk.Tk()
window.title("Number Guessing Game")
window.geometry("300x200")

# Create a label for instructions
instruction_label = tk.Label(window, text="Guess a number between 1 and 100:")
instruction_label.pack(pady=5)

# Create an entry widget for user input
entry = tk.Entry(window)
entry.pack(pady=5)

# Create a button that calls the check_guess function when clicked
guess_button = tk.Button(window, text="Guess", command=check_guess)
guess_button.pack(pady=5)

# Create a label to display the game's results and feedback
result_label = tk.Label(window, text="")
result_label.pack(pady=5)

# Create the 'Play Again' button but don't show it initially
play_again_button = tk.Button(window, text="Play Again", command=reset_game)

# Start the GUI event loop to keep the window open
window.mainloop()