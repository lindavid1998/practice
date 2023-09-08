import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PatternMatching {
    /**
     * Boyer Moore algorithm that relies on a last occurrence table.
     * This algorithm Works better with large alphabets.
     *
     * Make sure to implement the buildLastTable() method, which will be
     * used in this boyerMoore() method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class useful.
     *
     * You may assume that the passed in pattern, text, and comparator will not be null.
     *
     * @param pattern    The pattern you are searching for in a body of text.
     * @param text       The body of text where you search for the pattern.
     * @param comparator You MUST use this to check if characters are equal.
     * @return           List containing the starting index for each match found.
     */
    public static List<Integer> boyerMoore(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
      List<Integer> foundIndices = new ArrayList<Integer>();

      int textLength = text.length();
      int patternLength = pattern.length(); 
      if (textLength < patternLength) return foundIndices; 

      Map<Character, Integer> lastTable = buildLastTable(pattern);

      int textIndex = 0;
      while (textIndex <= textLength - patternLength) {
        int patternIndex = patternLength - 1;
        char textChar = '\0';
        char patternChar = '\0';
        while (patternIndex >= 0) {
          textChar = text.charAt(textIndex + patternIndex);
          patternChar = pattern.charAt(patternIndex);

          // if there is a match
          if (comparator.compare(textChar, patternChar) == 0) {
            patternIndex--; // go to the next char
          } else { // if there is a mismatch
            break;
          }
        }

        int lastOccurrenceIndex = lastTable.getOrDefault(textChar, -1);
        if (patternIndex == -1) {
          foundIndices.add(textIndex);
          textIndex++;
        } else if (lastOccurrenceIndex < patternIndex) {
          textIndex = textIndex + patternIndex - lastOccurrenceIndex;
        } else {
          textIndex++;
        }
    }
    return foundIndices;
  }
}


    /**
     * Builds the last occurrence table that will be used to run the Boyer Moore algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     * You may assume that the passed in pattern will not be null.
     *
     * @param pattern A pattern you are building last table for.
     * @return A Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern.
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        Map<Character, Integer> lastTable = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
          lastTable.put(pattern.charAt(i), i);
        }

        return lastTable;
    }




/**
 * Comparator that allows for comparison of characters and
 * counting said comparisons.
 *
 * This MUST BE USED for character comparisons. Using any other form of
 * comparison for characters will result in test failures.
 *
 * DO NOT CREATE INSTANCES OF THIS CLASS
 *
 * DO NOT MODIFY THIS FILE!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class CharacterComparator implements Comparator<Character> {

    private int comparisonCount;

    /**
     * To be used when comparing characters. Keeps count of
     * how many times this method has been called.
     *
     * @param a First character to be compared.
     * @param b Second character to be compared.
     * @return  Negative value if a is less than b, positive
     *          if a is greater than b, and 0 otherwise.
     */
    @Override
    public int compare(Character a, Character b) {
        comparisonCount++;
        return a - b;
    }

    /**
     * Gets the number of times compare has been used.
     *
     * @return The comparison count.
     */
    public int getComparisonCount() {
        return comparisonCount;
    }
}