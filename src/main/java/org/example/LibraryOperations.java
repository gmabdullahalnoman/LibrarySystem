package org.example;

import java.util.ArrayList;

public interface LibraryOperations {

    void addBook(Book book, User user);

    void displayBooks();

    void borrowBook(int id, User user);

    void returnBook(int id, User user);

    void updateBook(int id,
                    String title,
                    String author,
                    User user);

    void adjustStock(int id,
                     int amount,
                     boolean addStock,
                     String reason,
                     User user);

    void deleteBook(int id,
                    User user,
                    ArrayList<User> users);
}