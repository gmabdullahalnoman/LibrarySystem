package org.example.menu;

import org.example.model.AdminUser;
import org.example.service.AuthService;
import org.example.model.StudentUser;
import org.example.model.User;
import org.example.exception.AuthenticationException;
import org.example.util.InputUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthMenuHandler {

    private final AuthService authService;
    private int userIdCounter;

    public AuthMenuHandler(AuthService authService) {
        this.authService = authService;
        this.userIdCounter = 1;
    }
    public User handleAuthMenu(ArrayList<User> users, Scanner sc) {
/*
        System.out.println("\n===== Welcome =====");
        System.out.println("1. Register Student");
        System.out.println("2. Register Admin");
        System.out.println("3. Login");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
*/
        // new menu format
        System.out.println("===== Welcome =====");

        System.out.printf("%-25s %-25s%n",
                "1. Register Student",
                "2. Register Admin");
        System.out.printf("%-25s %-25s%n",
                "3. Login",
                "4. Forgot Password");
        System.out.printf("%-25s%n",
                "0. Exit");

        System.out.print("Choice: ");
        int choice = InputUtil.safeIntInput(sc);

        if (choice == -1) {
            return null;
        }
        switch (choice) {

            case 1:
                registerStudent(users, sc);
                break;

            case 2:
                registerAdmin(users, sc);
                break;

            case 3:
                return login(users, sc);

            case 4:
                forgotPassword(users, sc);
                break;

            case 0:
                System.exit(0);

            default:
                System.out.println("Invalid choice.");
        }

        return null;
    }
    private void registerStudent(ArrayList<User> users, Scanner sc) {

        User student = createUser(false, sc);

        if (student != null) {
            authService.registerUser(users, student);
        }
    }
    private void registerAdmin(ArrayList<User> users, Scanner sc) {

        System.out.print("Enter Admin Secret Key: ");
        String secret = sc.nextLine();

        if (!secret.equals("123")) {
            System.out.println("Invalid admin secret key.");
            return;
        }

        User admin = createUser(true, sc);

        if (admin != null) {
            authService.registerUser(users, admin);
        }
    }
    private User login(ArrayList<User> users, Scanner sc) {

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        try {
            return authService.loginUser(users, username, password);

        } catch (AuthenticationException e) {

            System.out.println(e.getMessage());
            return null;
        }
    }
    private User createUser(boolean isAdmin,
                            Scanner sc) {

        String name;

        while (true) {

            System.out.print("Enter Name: ");
            name = sc.nextLine().trim();

            if (!name.matches("[A-Za-z ]+")) {

                System.out.println(
                        "Name can contain only letters and spaces."
                );

                continue;
            }

            String[] parts = name.split("\\s+");

            StringBuilder builder =
                    new StringBuilder();

            for (String part : parts) {

                builder.append(
                        Character.toUpperCase(
                                part.charAt(0)
                        )
                );

                builder.append(
                        part.substring(1)
                                .toLowerCase()
                );

                builder.append(" ");
            }

            name = builder.toString().trim();

            break;
        }
        String username;

        while (true) {

            System.out.print("Enter Username: ");

            username =
                    sc.nextLine().trim();

            if (username.length() < 4) {

                System.out.println(
                        "Username must be at least 4 characters."
                );

                continue;
            }

            if (!username.matches("[A-Za-z0-9_]+")) {

                System.out.println(
                        "Username can contain only letters, digits and underscore."
                );

                continue;
            }

            if (username.matches("\\d+")) {

                System.out.println(
                        "Username cannot be only numbers."
                );

                continue;
            }

            break;
        }


        String password;

        while (true) {

            System.out.print("Enter Password: ");

            password =
                    sc.nextLine();

            if (password.length() < 4) {

                System.out.println(
                        "Password must be at least 4 characters."
                );

                continue;
            }

            if (!password.matches(".*[A-Z].*")) {

                System.out.println(
                        "Password needs an uppercase letter."
                );

                continue;
            }

            if (!password.matches(".*[a-z].*")) {

                System.out.println(
                        "Password needs a lowercase letter."
                );

                continue;
            }

            if (!password.matches(".*\\d.*")) {

                System.out.println(
                        "Password needs a digit."
                );

                continue;
            }

            break;
        }

        if (isAdmin) {
            return new AdminUser(
                    userIdCounter++,
                    name,
                    username,
                    password
            );
        }
        return new StudentUser(
                userIdCounter++,
                name,
                username,
                password
        );
    }
    private void forgotPassword(ArrayList<User> users, Scanner sc) {

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        User user = null;

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                user = u;
                break;
            }
        }

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (user.getSecurityQuestion() == null) {
            System.out.println("No security question set for this account.");
            return;
        }

        System.out.println("Security Question: " + user.getSecurityQuestion());

        System.out.print("Enter Answer: ");
        String answer = sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPassword = sc.nextLine();

        authService.forgotPassword(users, username, answer, newPassword);
    }
}