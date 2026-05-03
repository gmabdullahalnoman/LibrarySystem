package org.example;

import java.util.Scanner;

public class Main {
    static void main() {

                Library library = new Library();
                User user = new User(1, "Default User");
                Scanner scanner = new Scanner(System.in);

                // Sample books
                library.addBook(new Book(1, "Java Basics", "John Doe"));
                library.addBook(new Book(2, "OOP Concepts", "Jane Smith"));
                library.addBook(new Book(3, "Data Structures", "Alice Lee"));

                int choice;

                do {
                    System.out.println("\n===== Library Menu =====");
                    System.out.println("1. View Books");
                    System.out.println("2. Borrow Book");
                    System.out.println("3. Return Book");
                    System.out.println("4. View My Books");
                    System.out.println("0. Exit");
                    System.out.print("Enter choice: ");

                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            library.displayBooks();
                            break;

                        case 2:
                            System.out.print("Enter Book ID to borrow: ");
                            int borrowId = scanner.nextInt();
                            library.borrowBook(borrowId, user);
                            break;

                        case 3:
                            System.out.print("Enter Book ID to return: ");
                            int returnId = scanner.nextInt();
                            library.returnBook(returnId, user);
                            break;

                        case 4:
                            user.showBorrowedBooks();
                            break;

                        case 0:
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }

                } while (choice != 0);

                scanner.close();
            }
        }