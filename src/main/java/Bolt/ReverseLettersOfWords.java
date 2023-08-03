package Bolt;

/**
 * @author lzn
 * @date 2023/03/20 16:07
 * @description Question
 * Write an algorithm which reverses the letters of words in a sentence, like:
 * Example input: I drive with Bolt
 * Example output: I evird htiw tloB
 * <p>
 * The input is a character array (not a string).
 * <p>
 * The function needs to work in-place, modifying the input array itself. So that memory consumption should be constant (e.g. temporary variables are allowed).
 * <p>
 * Don't use any extra arrays, string objects or language-provided functions/libraries.
 * Written tests for your solution are highly preferred.
 * <p>
 * Example in Java:
 * <p>
 * Input: char[] input = ['I',' ','d','r','i','v','e',' ','w','i','t','h',' ','B','o','l','t'];
 * Output: ['I',' ','e','v','i','r','d',' ','h','t','i','w',' ','t','l','o','B'];
 */
public class ReverseLettersOfWords {
    /**
     * time: O(N), space: O(1)
     * step1: Initialize two pointers, one is at the beginning of the letters as a slow pointer and the other move to the end of the letters as a fast pointer
     * step2: If fast pointer move to the end of the word, reverse the total letters from start to end
     *
     * @param input
     * @return
     */
    public static char[] reverseWords(char[] input) {
        if (input == null) {
            throw new RuntimeException("the input char can not be null");
        }
        int length = input.length;
        if (length < 2) {
            return input;
        }
        int slow = 0, fast = 0;
        while (fast < length) {
            char curr = input[fast];
            if (curr == ' ') {
                reverseLetters(input, slow, fast - 1);
                fast++;
                slow = fast;
            } else {
                fast++;
            }
        }
        reverseLetters(input, slow, length - 1);
        return input;
    }

    /**
     * reverse the letters from start(inclusive) to end(inclusive)
     *
     * @param input
     * @param start
     * @param end
     */
    private static void reverseLetters(char[] input, int start, int end) {
        if (start >= end || end - start == 1) {
            return;
        }
        while (start < end) {
            char temp = input[start];
            input[start] = input[end];
            input[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        //['I',' ','e','v','i','r','d',' ','h','t','i','w',' ','t','l','o','B']
        System.out.println(reverseWords(new char[]{'I', ' ', 'd', 'r', 'i', 'v', 'e', ' ', 'w', 'i', 't', 'h', ' ', 'B', 'o', 'l', 't'}));
        //[' ','e','v','i','r','d',' ','h','t','i','w',' ','t','l','o','B']
        System.out.println(reverseWords(new char[]{' ', 'd', 'r', 'i', 'v', 'e', ' ', 'w', 'i', 't', 'h', ' ', 'B', 'o', 'l', 't'}));
        //[' ','e','v','i','r','d',' ','h','t','i','w',' ','t','l','o','B', ' ']
        System.out.println(reverseWords(new char[]{' ', 'd', 'r', 'i', 'v', 'e', ' ', 'w', 'i', 't', 'h', ' ', 'B', 'o', 'l', 't', ' '}));
    }
}
