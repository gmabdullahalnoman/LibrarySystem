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
    private User createUser(boolean isAdmin, Scanner sc) {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return null;
        }

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

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
}