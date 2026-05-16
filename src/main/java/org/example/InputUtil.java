package org.example;

import java.util.Scanner;

public class InputUtil {

    public static int safeIntInput(Scanner sc) {

        try {

            int value = sc.nextInt();
            sc.nextLine();

            return value;

        } catch (Exception e) {

            System.out.println("Invalid input.");
            sc.nextLine();

            return -1;
        }
    }
}