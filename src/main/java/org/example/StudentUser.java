package org.example;

public class StudentUser extends User {

    private static final int LIMIT = 2;

    public StudentUser(int id, String name) {
        super(id, name);
    }

    @Override
    public void borrowBook(Book book) {
        if (getBorrowedCount() >= LIMIT) {
            System.out.println("Student limit reached (2 books).");
            return;
        }
        super.borrowBook(book);
    }
}