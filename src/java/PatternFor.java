package java;
/* 
 * Haoxuan Jing
 * 11/04/2023 
 * CSE 122 BL
 * P2: Absurdle
 * TA: Helena Kaikkonen
 */

import java.util.*;

public class PatternFor {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    public static void main(String[] args) {
        System.out.println(patternFor("abbey", "bebop")); // Expected: 🟨🟨🟩⬜⬜
        System.out.println(patternFor("abbey", "ether")); // Expected: ⬜⬜⬜🟩⬜
        System.out.println(patternFor("abbey", "keeps")); // Expected: ⬜🟨⬜⬜⬜
        System.out.println(patternFor("bebop", "abbey")); // Expected: ⬜🟨🟩🟨⬜
    }

    /*
     * Returns a pattern string for the given word and guess. 
     * green for correct letter in correct position,
     * yellow for correct letter in wrong position,
     * gray for incorrect letter.
     * 
     * @param word: the word to guess
     * @param guess: the user's guess
     */
    public static String patternFor(String word, String guess) {
        List<String> letterList = new ArrayList<String>();
        Map<Character, Integer> letterCount = new HashMap<Character, Integer>();

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            char letterOfGuess = guess.charAt(i);

            // Add the letter to the letterList
            letterList.add(String.valueOf(letter));

            // Accumulate the letter to the map
            if (letterCount.containsKey(letter)) {
                letterCount.put(letter, letterCount.get(letter) + 1);
            } else {
                letterCount.put(letter, 1);
            }

            // Mark the letter as green if it is an exact match
            if (letter == letterOfGuess) {
                letterList.set(i, GREEN);
                letterCount.put(letter, letterCount.get(letter) - 1);
            }
        }

        for (int i = 0; i < word.length(); i++) {
            // Process the letter only if it is not green
            if (!letterList.get(i).equals(GREEN)) {
                // Mark the letter as yellow if it is an approximate match
                char letterOfGuess = guess.charAt(i);
                if (letterCount.containsKey(letterOfGuess) && letterCount.get(letterOfGuess) > 0) {
                    letterList.set(i, YELLOW);
                    letterCount.put(letterOfGuess, letterCount.get(letterOfGuess) - 1);
                }

                // Mark the letter as gray if it is not a match
                String letter = letterList.get(i);
                if (!letter.equals(GREEN) && !letter.equals(YELLOW)) {
                    letterList.set(i, GRAY);
                }
            }
        }

        // Generate the pattern from the list
        String pattern = "";
        for (String s : letterList) {
            pattern += s;
        }

        return pattern;
    }
}
