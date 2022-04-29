package bullscows;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            System.out.println("Input the length of the secret code:");
            int length = scanner.nextInt();
            if (length < 1) throw (new InputMismatchException());

            System.out.println("Input the number of possible symbols in the code:");
            int symbols = scanner.nextInt();

            if (symbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)");
                System.exit(0);
            }



            if (symbols < length) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.",
                        length, symbols);
                System.exit(0);
            }

            char[] secret = getSecret(length, symbols);

            StringBuilder range = new StringBuilder("0");
            if (symbols > 1) range.append("-").append(symbols > 10 ? 9 : symbols - 1);
            if (symbols > 10) range.append(", ").append('a');
            if (symbols > 11) range.append("-").append((char)(symbols - 11 + 'a'));
            System.out.printf("The secret is prepared: %s (%s).%n", "*".repeat(length), range);

            System.out.println("Okay, let's start a game!");
            int turn = 0;
            String gradle = "";
            while (!gradle.equals(length + (length > 1 ? " bulls" : " bull") )) {
                System.out.printf("Turn %d:%n", ++turn);
                char[] input = scanner.next().toCharArray();
                if (!checkInput(input, symbols, length)) {
                    System.out.printf("Error: \"%s\" isn't a valid number.", String.valueOf(input));
                }
                gradle = checkGradle(input, secret);
                System.out.printf("Gradle: %s%n", gradle);
            }
            System.out.println("Congratulations! You guessed the secret code.");

        } catch (InputMismatchException ime) {
            System.out.println("Error: ......");
            System.exit(0);
        }
    }


    static char[] getSecret(int length, int symbols) {
        char[] secret = new char[length];
        boolean[] symbolUsed = new boolean[symbols];
        int pos = 0;
        while (pos < length) {
            int next = (int)(Math.random() * symbols);
            if (!symbolUsed[next]) {
                symbolUsed[next] = true;
                secret[pos++] = (char)(next < 10 ? next + '0' : next - 10 + 'a');
            }
        }
        return secret;
    }

    static String checkGradle(char[] input, char[] secret) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < input.length; i++)
            for (int j = 0; j < secret.length; j++) {
                if (input[i] == secret[j])
                    if (i == j) {
                        bulls++;
                    } else cows++;
            }
        StringBuilder gradle = new StringBuilder();
        if (bulls > 0) gradle.append(bulls).append(bulls == 1 ? " bull" : " bulls");
        if (bulls > 0 & cows > 0) gradle.append(" and ");
        if (cows > 0) gradle.append(cows).append(cows == 1 ? " cow" : " cows");
        return bulls + cows == 0 ? "None" : gradle.toString();
    }

    static boolean checkInput(char[] input, int symbols, int length) {
        if (input.length != length) return false;
        for (char ch : input) {
            int value = ch < 'a' ? ch - '0' : ch - 'a';
            if (value < 0 || value >= symbols) return false;
        }
        return true;
    }
}


