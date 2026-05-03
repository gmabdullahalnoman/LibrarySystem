package org.example;

public class AdminUser extends User {

    public AdminUser(int id, String name) {
        super(id, name);
    }

    @Override
    public void borrowBook(Book book) {
        // Admin has no limit
        super.borrowBook(book);
    }
}