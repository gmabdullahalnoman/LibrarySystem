package org.example.menu;
import org.example.service.LibraryService;
import org.example.model.User;
import org.example.exception.BookNotFoundException;
import org.example.exception.InvalidOperationException;
import org.example.util.InputUtil;
import org.example.service.AuthService;

import java.util.Scanner;

public class UserMenuHandler {

    private final LibraryService library;
    private final AuthService authService;

    public UserMenuHandler(LibraryService library, AuthService authService) {
        this.library = library;
        this.authService = authService;
    }

    public User handleUserMenu(User currentUser,
                               Scanner sc) {

        if (currentUser.getStatus() != User.Status.ACTIVE) {
            return handlePendingMenu(currentUser, sc);
        }
/*
        System.out.println("\n===== User Menu =====");
        System.out.println("1. Display Books");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. My Books");
        System.out.println("5. Request Premium");
        System.out.println("6. Transaction History");
        System.out.println("7. Logout");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
*/
        //new menu format
        System.out.println("===== User Menu =====");

        System.out.printf("%-25s %-25s %-25s%n",
                "1. Display Books",
                "2. Borrow Book",
                "3. Return Book");

        System.out.printf("%-25s %-25s %-25s%n",
                "4. My Books",
                "5. Request Premium",
                "6. Transaction History");

        System.out.printf("%-25s %-25s %-25s%n",
                "7. Change Password",
                "8. Logout",
                "0. Exit");

        System.out.print("Choice: ");
        int choice = InputUtil.safeIntInput(sc);

        if (choice == -1) {
            return currentUser;
        }

        switch (choice) {

            case 1:
                library.displayBooks();
                break;

            case 2:
                System.out.print("Book ID: ");

                try {

                    library.borrowBook(
                            InputUtil.safeIntInput(sc),
                            currentUser
                    );

                } catch (BookNotFoundException |
                         InvalidOperationException e) {

                    System.out.println(e.getMessage());
                }

                break;

            case 3:
                System.out.print("Book ID: ");
                library.returnBook(
                        InputUtil.safeIntInput(sc),
                        currentUser
                );
                break;

            case 4:
                currentUser.showBorrowedBooks();
                break;

            case 5:
                currentUser.requestPremium();
                break;

            case 6:
                library.displayUserTransactions(currentUser);
                break;

            case 7:
                System.out.print("Current Password: ");
                String currentPass = sc.nextLine();

                System.out.print("New Password: ");
                String newPass = sc.nextLine();

                authService.changePassword(currentUser, currentPass, newPass);
                break;

            case 8:
                return null;

            case 0:
                System.exit(0);
        }

        return currentUser;
    }

    private User handlePendingMenu(User currentUser,
                                   Scanner sc) {
        if (currentUser.getStatus() == User.Status.REJECTED) {

            System.out.println("\n===== Account Rejected =====");

            System.out.println(
                    "Reason: " +
                            currentUser.getRejectionReason()
            );

            System.out.println("1. Resubmit Activation Request");
            System.out.println("2. View My Status");
            System.out.println("3. Logout");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = InputUtil.safeIntInput(sc);

            switch (choice) {

                case 1:
                    currentUser.resubmitActivationRequest();
                    break;

                case 2:
                    currentUser.showBorrowedBooks();
                    break;

                case 3:
                    return null;

                case 0:
                    System.exit(0);
            }

            return currentUser;
        }

        System.out.println("\n===== Account Pending =====");
        System.out.println("Status: " + currentUser.getStatus());

        if (currentUser.getStatus() == User.Status.PENDING) {

            if (currentUser.isActivationRequested()) {

                System.out.println(">> Activation request sent & pending.");

            } else {

                System.out.println(">> Send request to activate your account.");
                System.out.println("1. Request Activation");
            }
        }

        System.out.println("2. View My Status");
        System.out.println("3. Logout");
        System.out.println("0. Exit");
        System.out.print("Choice: ");

        int choice = InputUtil.safeIntInput(sc);

        switch (choice) {

            case 1:
                currentUser.requestActivation();
                break;

            case 2:
                currentUser.showBorrowedBooks();
                break;

            case 3:
                return null;

            case 0:
                System.exit(0);
        }

        return currentUser;
    }
}