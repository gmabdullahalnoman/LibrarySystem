package org.example;

public interface LibraryOperations {
    void addBook(Book book, User user);
    void displayBooks();
    void borrowBook(int id, User user);
    void returnBook(int id, User user);

    void updateBook(int id, String title, String author, int quantity, User user);
    void deleteBook(int id, User user);
}