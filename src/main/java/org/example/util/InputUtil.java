package org.example.util;

import java.util.Scanner;

public class InputUtil {

    public static int safeIntInput(Scanner sc) {

        while (true) {

            try {

                int value = sc.nextInt();
                sc.nextLine();

                return value;

            } catch (Exception e) {

                sc.nextLine();

                System.out.print(
                        "Invalid input! Please enter a valid integer: "
                );
            }
        }
    }

    public static boolean isValidName(String name) {

        return name.matches("[A-Za-z ]{2,50}");
    }

    public static String formatName(String name) {

        String[] parts =
                name.trim()
                        .toLowerCase()
                        .split("\\s+");

        StringBuilder formatted =
                new StringBuilder();

        for (String part : parts) {

            formatted.append(
                    Character.toUpperCase(
                            part.charAt(0)
                    )
            );

            formatted.append(
                    part.substring(1)
            );

            formatted.append(" ");
        }

        return formatted.toString().trim();
    }

    public static boolean isValidUsername(String username) {

        return username.matches(
                "^[A-Za-z][A-Za-z0-9_.]{3,19}$"
        );
    }

    public static boolean isValidPassword(String password) {

        return password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{4,}$"
        );
    }

}