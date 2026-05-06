package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int userIdCounter = 1;

    static void main() {

        LibraryService library = new LibraryService();
        ArrayList<User> users = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        try {
            User currentUser = null;

            while (true) {

                // AUTH MENU
                if (currentUser == null) {
                    System.out.println("to solve - admin can still approve without student sends approve request");
                    System.out.println("\n===== Welcome =====");
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("0. Exit");
                    System.out.print("Choice: ");

                    int auth = safeIntInput(sc);
                    if (auth == -1) continue;

                    switch (auth) {
                        case 1:
                            System.out.println("1. Student");
                            System.out.println("2. Admin");
                            System.out.print("Choice: ");

                            int type = safeIntInput(sc);
                            if (type == -1) continue;

                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();

                            if (name.isEmpty()) {
                                System.out.println("Name cannot be empty.");
                                continue;
                            }

                            User newUser;
                            if (type == 1) {
                                newUser = new StudentUser(userIdCounter++, name);
                            } else if (type == 2) {
                                newUser = new AdminUser(userIdCounter++, name);
                            } else {
                                System.out.println("Invalid type.");
                                continue;
                            }

                            users.add(newUser);
                            System.out.println("Registered. ID: " + newUser.getId());
                            break;

                        case 2:
                            System.out.print("Enter ID: ");
                            int id = safeIntInput(sc);
                            if (id == -1) continue;

                            currentUser = findUserById(users, id);
                            if (currentUser == null) {
                                System.out.println("User not found.");
                            } else {
                                System.out.println("Welcome " + currentUser.getName());
                            }
                            break;

                        case 0:
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                    continue;
                }

                // ADMIN MENU
                if (currentUser instanceof AdminUser) {

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
                    System.out.println("13. Logout");
                    System.out.println("0. Exit");

                    int choice = safeIntInput(sc);
                    if (choice == -1) continue;

                    switch (choice) {

                        case 1:
                            System.out.print("Title: ");
                            String t = sc.nextLine();
                            System.out.print("Author: ");
                            String a = sc.nextLine();
                            System.out.print("Quantity: ");
                            int q = safeIntInput(sc);
                            if (q <= 0) break;

                            library.addBook(new Book(t, a, q), currentUser);
                            break;

                        case 2:
                            library.displayBooks();
                            break;

                        case 3:
                            System.out.print("Book ID: ");
                            int bid = safeIntInput(sc);
                            if (bid == -1) break;

                            System.out.print("New Title: ");
                            String nt = sc.nextLine();

                            System.out.print("New Author: ");
                            String na = sc.nextLine();

                            System.out.print("New Qty: ");
                            int nq = safeIntInput(sc);

                            library.updateBook(bid, nt, na, nq, currentUser);
                            break;

                        case 4:
                            System.out.print("Enter Book ID: ");
                            int delId = safeIntInput(sc);
                            if (delId == -1) break;

                            library.deleteBook(delId, currentUser, users);
                            break;

                        case 5: // NEW
                            library.displayUsers(users);
                            break;

                        case 6:
                            System.out.print("User ID: ");
                            library.approveUser(safeIntInput(sc), users, currentUser);
                            break;

                        case 7:
                            System.out.print("User ID: ");
                            library.rejectUser(safeIntInput(sc), users, currentUser);
                            break;

                        case 8:
                            System.out.print("User ID: ");
                            library.approvePremium(safeIntInput(sc), users, currentUser);
                            break;

                        case 9:
                            System.out.print("User ID: ");
                            library.setUserBlock(safeIntInput(sc), true, users, currentUser);
                            break;

                        case 10:
                            System.out.print("User ID: ");
                            library.setUserBlock(safeIntInput(sc), false, users, currentUser);
                            break;

                        case 11:
                            System.out.print("User ID: ");
                            int uid = safeIntInput(sc);
                            System.out.print("New Limit: ");
                            int lim = safeIntInput(sc);
                            library.updateUserLimit(uid, lim, users, currentUser);
                            break;

                        case 12:
                            System.out.print("User ID: ");
                            library.deleteUser(safeIntInput(sc), users, currentUser);
                            break;

                        case 13:
                            currentUser = null;
                            break;

                        case 0:
                            return;
                    }

                } else {

                    // If NOT active → restricted menu
                    if (currentUser.getStatus() != User.Status.ACTIVE) {

                        System.out.println("\n===== Account Pending =====");
                        System.out.println("Status: " + currentUser.getStatus());

                        if (currentUser.getStatus() == User.Status.PENDING) {
                            System.out.println(">> Your account is waiting for admin approval.");
                            System.out.println("1. Request Activation");
                        }

                        System.out.println("2. View My Status");
                        System.out.println("3. Logout");
                        System.out.println("0. Exit");
                        System.out.print("Choice: ");

                        int choice = safeIntInput(sc);
                        if (choice == -1) continue;

                        switch (choice) {

                            case 1:
                                if (currentUser.getStatus() == User.Status.PENDING) {
                                    currentUser.requestActivation();
                                } else {
                                    System.out.println("Invalid choice.");
                                }
                                break;

                            case 2:
                                currentUser.showBorrowedBooks();
                                break;

                            case 3:
                                currentUser = null;
                                break;

                            case 0:
                                return;

                            default:
                                System.out.println("Invalid choice.");
                        }

                        continue;
                    }
                    // USER MENU
                    System.out.println("\n===== User Menu =====");
                    System.out.println("1. Display Books");
                    System.out.println("2. Borrow Book");
                    System.out.println("3. Return Book");
                    System.out.println("4. My Books");
                    System.out.println("5. Request Premium");
                    System.out.println("6. Logout");
                    System.out.println("0. Exit");
                    System.out.print("Choice: ");

                    int choice = safeIntInput(sc);
                    if (choice == -1) continue;

                    switch (choice) {

                        case 1:
                            library.displayBooks();
                            break;

                        case 2:
                            System.out.print("Book ID: ");
                            library.borrowBook(safeIntInput(sc), currentUser);
                            break;

                        case 3:
                            System.out.print("Book ID: ");
                            library.returnBook(safeIntInput(sc), currentUser);
                            break;

                        case 4:
                            currentUser.showBorrowedBooks();
                            break;

                        case 5:
                            currentUser.requestPremium();
                            break;

                        case 6:
                            currentUser = null;
                            break;

                        case 0:
                            return;
                    }
                }
            }

        } finally {
            sc.close();
        }
    }

    private static int safeIntInput(Scanner sc) {
        try {
            int val = sc.nextInt();
            sc.nextLine();
            return val;
        } catch (Exception e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return -1;
        }
    }

    private static User findUserById(ArrayList<User> users, int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}