package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int userIdCounter = 1;

    static void main() {

        LibraryOperations library = new LibraryService();
        ArrayList<User> users = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        try {
            User currentUser = null;

            while (true) {

                // Register & Login Menu
                if (currentUser == null) {
                    System.out.println("\n===== Welcome =====");
                    System.out.println("1. Sign-Up (Register New)");
                    System.out.println("2. Login (Existing Account)");
                    System.out.println("0. Exit");
                    System.out.print("Choice: ");

                    int authChoice = safeIntInput(sc);
                    if (authChoice == -1) continue;

                    switch (authChoice) {
                        case 1:
                            System.out.println("Select User Type:");
                            System.out.println("1. Student");
                            System.out.println("2. Premium");
                            System.out.println("3. Admin");
                            System.out.print("Choice: ");

                            int type = safeIntInput(sc);
                            if (type == -1) continue;

                            System.out.print("Enter User Name: ");
                            String name = sc.nextLine();

                            if (name.isEmpty()) {
                                System.out.println("User Name cannot be empty.");
                                continue;
                            }

                            User newUser;

                            switch (type) {
                                case 1:
                                    newUser = new StudentUser(userIdCounter++, name);
                                    break;
                                case 2:
                                    newUser = new PremiumUser(userIdCounter++, name);
                                    break;
                                case 3:
                                    newUser = new AdminUser(userIdCounter++, name);
                                    break;
                                default:
                                    System.out.println("Invalid type.");
                                    continue;
                            }
                            users.add(newUser);
                            System.out.println("Registered. Your ID: " + newUser.getId());
                            break;
                        case 2:
                            System.out.print("Enter User ID: ");
                            int loginId = safeIntInput(sc);
                            if (loginId == -1) continue;

                            currentUser = findUserById(users, loginId);
                            if (currentUser == null) {
                                System.out.println("User not found.");
                            } else {
                                System.out.println("Welcome " + currentUser.getName() +" to the Library.");
                            }
                            break;
                        case 0:
                            System.out.println("Exiting...");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                    continue;
                }

                // LIBRARY MENU
                System.out.println("\n===== Library Menu =====");

                if (currentUser instanceof AdminUser) {
                    System.out.println("1. Add Book");
                    System.out.println("2. Display Books");
                    System.out.println("3. Logout");
                    System.out.println("0. Exit");
                } else {
                    System.out.println("1. Display Books");
                    System.out.println("2. Borrow Book");
                    System.out.println("3. Return Book");
                    System.out.println("4. View My Books");
                    System.out.println("5. Logout");
                    System.out.println("0. Exit");
                }

                System.out.print("Choose: ");
                int choice = safeIntInput(sc);
                if (choice == -1) continue;

                if (currentUser instanceof AdminUser) {

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Title: ");
                            String title = sc.nextLine();

                            System.out.print("Enter Author: ");
                            String author = sc.nextLine();

                            if (title.isEmpty() || author.isEmpty()) {
                                System.out.println("Title/Author cannot be empty.");
                                break;
                            }

                            System.out.print("Enter Quantity: ");
                            int qty = safeIntInput(sc);
                            if (qty == -1) break;

                            if (qty <= 0) {
                                System.out.println("Quantity must be positive.");
                                break;
                            }

                            library.addBook(new Book(title, author, qty), currentUser);
                            break;

                        case 2:
                            library.displayBooks();
                            break;

                        case 3:
                            currentUser = null;
                            System.out.println("Logged out.");
                            break;

                        case 0:
                            System.out.println("Exiting...");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } else {
                    switch (choice) {
                        case 1:
                            library.displayBooks();
                            break;

                        case 2:
                            System.out.print("Enter Book ID: ");
                            int borrowId = safeIntInput(sc);
                            if (borrowId == -1) break;

                            library.borrowBook(borrowId, currentUser);
                            break;

                        case 3:
                            System.out.print("Enter Book ID: ");
                            int returnId = safeIntInput(sc);
                            if (returnId == -1) break;

                            library.returnBook(returnId, currentUser);
                            break;

                        case 4:
                            currentUser.showBorrowedBooks();
                            break;

                        case 5:
                            currentUser = null;
                            System.out.println("Logged out.");
                            break;

                        case 0:
                            System.out.println("Exiting...");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }

        } finally {
            sc.close();
        }
    }

    // reusable safe input
    private static int safeIntInput(Scanner sc) {
        try {
            int val = sc.nextInt();
            sc.nextLine();
            return val;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.nextLine();
            return -1;
        }
    }

    private static User findUserById(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }
}