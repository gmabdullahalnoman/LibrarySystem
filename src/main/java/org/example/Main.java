package org.example;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    private static int userIdCounter = 1;
    public static void main(String[] args) {

        LibraryOperations library = new LibraryService();
        ArrayList<User> users = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        try {
            User currentUser = null;
            while (true) {
                // LOGIN / REGISTER MENU
                if (currentUser == null) {
                    System.out.println("\n===== Welcome =====");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("0. Exit");
                    System.out.print("Choice: ");
                    int authChoice = sc.nextInt();
                    sc.nextLine();
                    switch (authChoice) {
                        case 1:
                            System.out.println("Select User Type:");
                            System.out.println("1. Student");
                            System.out.println("2. Premium");
                            System.out.println("3. Admin");
                            System.out.print("Choice: ");
                            int type = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();

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
                            System.out.println("Registered successfully. Your ID: " + newUser.getId());
                            break;
                        case 2:
                            System.out.print("Enter User ID: ");
                            int loginId = sc.nextInt();
                            currentUser = findUserById(users, loginId);
                            if (currentUser == null) {
                                System.out.println("User not found.");
                            } else {
                                System.out.println("Login successful. Welcome " + currentUser.getName());
                            }
                            break;
                        case 0:
                            System.out.println("Exiting system...");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                    continue;
                }
                // LIBRARY MENU
                System.out.println("\n===== Library Menu =====");
                System.out.println("1. Add Book");
                System.out.println("2. Display Books");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. View My Books");
                System.out.println("6. Logout");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                int choice;

                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    sc.nextLine();
                    continue;
                }

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

                        library.addBook(new Book(title, author));
                        break;

                    case 2:
                        library.displayBooks();
                        break;

                    case 3:
                        System.out.print("Enter Book ID: ");
                        int borrowId = sc.nextInt();
                        library.borrowBook(borrowId, currentUser);
                        break;

                    case 4:
                        System.out.print("Enter Book ID: ");
                        int returnId = sc.nextInt();
                        library.returnBook(returnId, currentUser);
                        break;

                    case 5:
                        currentUser.showBorrowedBooks();
                        break;

                    case 6:
                        currentUser = null;
                        System.out.println("Logged out.");
                        break;

                    case 0:
                        System.out.println("Exiting system...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } finally {
            sc.close();
        }
    }
    private static User findUserById(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }
}