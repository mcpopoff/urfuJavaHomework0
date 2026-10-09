package org.example.utils;

import java.util.Scanner;

public class ConsoleReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        }  catch (NumberFormatException e) {
            throw new NumberFormatException("You need to type a number");
        }
    }

    public static long readLong() {
        String input = scanner.nextLine();
        try {
            return Long.parseLong(input);
        }  catch (NumberFormatException e) {
            throw new NumberFormatException("You need to type a number");
        }
    }

    public static String readString() {
        return scanner.nextLine();
    }


}
