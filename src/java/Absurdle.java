package java;
/* 
 * Haoxuan Jing
 * 11/04/2023 
 * CSE 122 BL
 * P2: Absurdle
 * TA: Helena Kaikkonen
 */

import java.util.*;
import java.io.*;

public class Absurdle {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = record(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        console.close();
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word.
    // Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns
    // it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    /*
     * Loads the contents of a given file Scanner into a List<String> and returns
     * 
     * @param dictScan: contains file contents
     * 
     * @return the list of words
     */
    public static Set<String> pruneDictionary(List<String> contents, int wordLength) {
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }

        Set<String> words = new HashSet<String>();
        for (String word : contents) {
            if (word.length() == wordLength) {
                words.add(word);
            }
        }

        return words;
    }

    /*
     * Give the user's guess and the words set, return the pattern of the guess and
     * update the words set.
     * 
     * @param guess: the user's guess
     * 
     * @param words: the set of words
     * 
     * @param wordLength: the length of the word
     * 
     * @return the pattern of the guess
     */
    public static String record(String guess, Set<String> words, int wordLength) {
        // Check if the guess is valid
        if (guess.length() != wordLength || words.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // Generate pattern map for each possible word
        Map<String, HashSet<String>> patternMap = new TreeMap<>();
        for (String word : words) {
            String pattern = patternFor(word, guess);
            if (!patternMap.containsKey(pattern)) {
                patternMap.put(pattern, new HashSet<>());
            }
            patternMap.get(pattern).add(word);
        }

        // Find the most common value
        String mostCommonValue = findKeyWithMostValue(patternMap);
        System.out.print(mostCommonValue);

        // Update the words set
        words.retainAll(patternMap.get(mostCommonValue));

        return mostCommonValue;
    }

    /*
     * Returns a pattern string for the given word and guess.
     * green for correct letter in correct position,
     * yellow for correct letter in wrong position,
     * gray for incorrect letter.
     * 
     * @param word: the word to guess
     * 
     * @param guess: the user's guess
     * 
     * @return the pattern string
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

    /*
     * Find the key with the most value in the given map.
     * 
     * @param patternMap: the map to find the key with most value
     * 
     * @return the key with most value
     */
    public static String findKeyWithMostValue(Map<String, HashSet<String>> patternMap) {
        int currentSize = 0;
        String currentPattern = "";
        for (String pattern : patternMap.keySet()) {
            if (patternMap.get(pattern).size() > currentSize) {
                currentSize = patternMap.get(pattern).size();
                currentPattern = pattern;
            }
        }
        return currentPattern;
    }
}
