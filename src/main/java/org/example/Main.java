package org.example;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        LibraryOperations library = new LibraryService();
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Select User Type:");
            System.out.println("1. Student");
            System.out.println("2. Premium");
            System.out.println("3. Admin");
            System.out.print("Choice: ");

            int type = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            User user;

            switch (type) {
                case 1:
                    user = new StudentUser(1, name);
                    break;
                case 2:
                    user = new PremiumUser(1, name);
                    break;
                case 3:
                    user = new AdminUser(1, name);
                    break;
                default:
                    System.out.println("Invalid type, defaulting to Student.");
                    user = new StudentUser(1, name);
            }
            while (true) {
                System.out.println("\n===== Library Menu =====");
                System.out.println("1. Add Book");
                System.out.println("2. Display Books");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. View My Books");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                int choice;

                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
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
                        System.out.print("Enter Book ID to borrow: ");
                        int borrowId = sc.nextInt();
                        library.borrowBook(borrowId, user);
                        break;

                    case 4:
                        System.out.print("Enter Book ID to return: ");
                        int returnId = sc.nextInt();
                        library.returnBook(returnId, user);
                        break;

                    case 5:
                        user.showBorrowedBooks();
                        break;

                    case 0:
                        System.out.println("Exiting system...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}