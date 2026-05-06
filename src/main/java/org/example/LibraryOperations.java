package org.example;

public interface LibraryOperations {
    void addBook(Book book, User user);
    void displayBooks();
    void borrowBook(int id, User user);
    void returnBook(int id, User user);

    void updateBook(int id, String title, String author, int quantity, User user);
    void deleteBook(int id, User user);

    void approveUser(int userId, java.util.ArrayList<User> users, User admin);
    void rejectUser(int userId, java.util.ArrayList<User> users, User admin);

    void approvePremium(int userId, java.util.ArrayList<User> users, User admin);
    void setUserBlock(int userId, boolean block, java.util.ArrayList<User> users, User admin);
    void updateUserLimit(int userId, int limit, java.util.ArrayList<User> users, User admin);
    void deleteUser(int userId, java.util.ArrayList<User> users, User admin);
}