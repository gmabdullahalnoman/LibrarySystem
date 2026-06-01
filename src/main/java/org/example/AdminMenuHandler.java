package org.example;

import org.example.exception.AccessDeniedException;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenuHandler {

    private final LibraryService library;
    private final UserService userService;

    public AdminMenuHandler(LibraryService library,
                            UserService userService) {

        this.library = library;
        this.userService = userService;
    }
    public User handleAdminMenu(User currentUser,
                                ArrayList<User> users,
                                Scanner sc) {
/*
        System.out.println("\n===== Admin Menu =====");
        System.out.println("1. Add Book");
        System.out.println("2. Display Books");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. View All Users");
        System.out.println("6. Approve User");
        System.out.println("7. Reject User");
        System.out.println("8. Approve Premium");
        System.out.println("9. Block User");
        System.out.println("10. Unblock User");
        System.out.println("11. Update User Limit");
        System.out.println("12. Delete User");
        System.out.println("13. View Transactions");
        System.out.println("14. Logout");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
*/
        //New menu designed
        System.out.println("========== Admin Menu ==========");

        System.out.printf("%-30s %-30s %-30s%n",
                "1. Add Book",
                "2. Display Books",
                "3. Update Book");

        System.out.printf("%-30s %-30s %-30s%n",
                "4. Delete Book",
                "5. View All Users",
                "6. Manage Activations");

        System.out.printf("%-30s %-30s %-30s%n",
                "7. Approve Premium",
                "8. Block User",
                "9. Unblock User");

        System.out.printf("%-30s %-30s %-30s%n",
                "10. Update User Limit",
                "11. Delete User",
                "12. View Transactions");

        System.out.printf("%-30s %-30s %-30s%n",
                "13. Logout",
                "0. Exit",
                "");

        System.out.println("Choice: ");
        int choice = InputUtil.safeIntInput(sc);

        if (choice == -1) {
            return currentUser;
        }
        switch (choice) {

            case 1:
                addBook(currentUser, sc);
                break;

            case 2:
                try {

                    library.displayBooks();

                } catch (RuntimeException e) {

                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                updateBook(currentUser, sc);
                break;

            case 4:
                deleteBook(currentUser, users, sc);
                break;

            case 5:
                userService.displayUsers(users);
                break;

            case 6:
                manageActivationRequests(currentUser, users, sc);
                break;

            case 7:
                approvePremium(users, sc);
                break;

            case 8:
                blockUser(currentUser, users, sc);
                break;

            case 9:
                unblockUser(currentUser, users, sc);
                break;

            case 10:
                updateUserLimit(currentUser, users, sc);
                break;

            case 11:
                deleteUser(currentUser, users, sc);
                break;

            case 12:
                library.displayAllTransactions();
                break;

            case 13:
                return null;

            case 0:
                System.exit(0);
        }

        return currentUser;
    }
    private void addBook(User currentUser, Scanner sc) {

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Author: ");
        String author = sc.nextLine();

        System.out.print("Quantity: ");
        int quantity = InputUtil.safeIntInput(sc);

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        try {

            library.addBook(
                    new Book(title, author, quantity),
                    currentUser
            );

        } catch (AccessDeniedException e) {

            System.out.println(e.getMessage());
        }
    }


    private void updateBook(User currentUser, Scanner sc) {

        if (!library.hasBooks()) {
            System.out.println("No books available.");
            return;
        }

        library.displayBooks();

        System.out.print("Book ID: ");
        int id = InputUtil.safeIntInput(sc);

        System.out.print("New Title: ");
        String title = sc.nextLine();

        System.out.print("New Author: ");
        String author = sc.nextLine();

        System.out.print("New Quantity: ");
        int quantity = InputUtil.safeIntInput(sc);

        library.updateBook(id, title, author, quantity, currentUser);
    }
    private void deleteBook(User currentUser,
                            ArrayList<User> users,
                            Scanner sc) {

        if (!library.hasBooks()) {
            System.out.println("No books available.");
            return;
        }

        library.displayBooks();

        System.out.print("Book ID: ");
        int id = InputUtil.safeIntInput(sc);

        try {

            library.deleteBook(id, currentUser, users);

        } catch (AccessDeniedException e) {

            System.out.println(e.getMessage());
        }
    }
    private void manageActivationRequests(User currentUser,
                                          ArrayList<User> users,
                                          Scanner sc) {

        boolean found =
                userService.displayActivationRequests(users);

        if (!found) {
            return;
        }

        System.out.print("User ID: ");

        int userId =
                InputUtil.safeIntInput(sc);

        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.print("Action: ");

        int action =
                InputUtil.safeIntInput(sc);

        switch (action) {

            case 1:

                userService.approveUser(
                        userId,
                        users,
                        currentUser
                );

                break;

            case 2:

                System.out.print(
                        "Rejection reason: "
                );

                String reason =
                        sc.nextLine();

                userService.rejectUser(
                        userId,
                        users,
                        currentUser,
                        reason
                );

                break;

            default:
                System.out.println(
                        "Invalid action."
                );
        }
    }

    private void approveUser(User currentUser,
                             ArrayList<User> users,
                             Scanner sc) {

        boolean found = userService.displayActivationRequests(users);

        if (!found) {
            return;
        }

        System.out.print("User ID: ");

        userService.approveUser(
                InputUtil.safeIntInput(sc),
                users,
                currentUser
        );
    }
    private void rejectUser(User currentUser,
                            ArrayList<User> users,
                            Scanner sc) {

        boolean found = userService.displayActivationRequests(users);

        if (!found) {
            return;
        }

        System.out.print("User ID: ");

        int userId = InputUtil.safeIntInput(sc);

        System.out.print("Rejection reason: ");
        String reason = sc.nextLine();

        userService.rejectUser(
                userId,
                users,
                currentUser,
                reason
        );
    }

    private void approvePremium(ArrayList<User> users,
                                Scanner sc) {

        boolean found = userService.displayPremiumRequests(users);

        if (!found) {
            return;
        }

        System.out.print("User ID: ");

        userService.approvePremium(
                InputUtil.safeIntInput(sc),
                users
        );
    }
    private void blockUser(User currentUser,
                           ArrayList<User> users,
                           Scanner sc) {

        userService.displayUsers(users);

        System.out.print("User ID: ");

        userService.setUserBlock(
                InputUtil.safeIntInput(sc),
                true,
                users,
                currentUser
        );
    }

    private void unblockUser(User currentUser,
                             ArrayList<User> users,
                             Scanner sc) {

        boolean found = userService.displayBlockedUsers(users);

        if (!found) {
            return;
        }

        System.out.print("User ID: ");

        userService.setUserBlock(
                InputUtil.safeIntInput(sc),
                false,
                users,
                currentUser
        );
    }
    private void updateUserLimit(User currentUser,
                                 ArrayList<User> users,
                                 Scanner sc) {

        System.out.print("User ID: ");
        int id = InputUtil.safeIntInput(sc);

        System.out.print("New Limit: ");
        int limit = InputUtil.safeIntInput(sc);

        userService.updateUserLimit(
                id,
                limit,
                users,
                currentUser
        );
    }

    private void deleteUser(User currentUser,
                            ArrayList<User> users,
                            Scanner sc) {

        userService.displayUsers(users);

        System.out.print("User ID: ");

        userService.deleteUser(
                InputUtil.safeIntInput(sc),
                users,
                currentUser
        );
    }
}