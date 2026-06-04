package org.example;

import org.example.exception.InvalidIntInputException;

import java.util.Scanner;

public class InputUtil {

    public static int safeIntInput(Scanner sc) {

        while (true) {
            try {
                int value = sc.nextInt();
                sc.nextLine(); // buffer clear
                return value;  // valid input → exit loop

            } catch (Exception e) {

                sc.nextLine(); // wrong input clear

                System.out.print("Invalid input! Please enter a valid integer: ");
            }
        }
    }
}