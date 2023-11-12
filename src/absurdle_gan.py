from typing import Final
from collections import Counter

GREEN: Final[str] = "🟩"
YELLOW: Final[str] = "🟨"
GREY: Final[str] = "⬜"


def pattern(word: str, guess: str) -> str:
    """Returns a pattern string for the given word and guess.
    green for correct letter in correct position,
    yellow for correct letter in wrong position,
    gray for incorrect letter.
    """

    pattern = ""
    word_counts = Counter(word) # Count all letters in word

    # Subtract all correct letters from word_counts
    word_counts.subtract(i for i, j in zip(word, guess) if i == j)

    # Add green, yellow, and grey letters to pattern
    for word_letter, guess_letter in zip(word, guess):
        if word_letter == guess_letter:
            pattern += GREEN
        elif (guess_letter in word) and (word_counts[guess_letter] > 0):
            word_counts.subtract(guess_letter)
            pattern += YELLOW
        else:
            pattern += GREY

    return pattern
