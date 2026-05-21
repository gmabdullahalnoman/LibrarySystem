package org.example;
import org.example.exception.BookNotFoundException;
import org.example.exception.InvalidOperationException;

import java.util.Scanner;

public class UserMenuHandler {

    private final LibraryService library;

    public UserMenuHandler(LibraryService library) {
        this.library = library;
    }

    public User handleUserMenu(User currentUser,
                               Scanner sc) {

        if (currentUser.getStatus() != User.Status.ACTIVE) {
            return handlePendingMenu(currentUser, sc);
        }

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
                return null;

            case 0:
                System.exit(0);
        }

        return currentUser;
    }

    private User handlePendingMenu(User currentUser,
                                   Scanner sc) {

        System.out.println("\n===== Account Pending =====");
        System.out.println("Status: " + currentUser.getStatus());

        if (currentUser.getStatus() == User.Status.PENDING) {

            if (currentUser.isActivationRequested()) {

                System.out.println(">> Activation request pending.");

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