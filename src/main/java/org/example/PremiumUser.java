package org.example;

public class PremiumUser extends User {

    private static final int LIMIT = 5;

    public PremiumUser(int id, String name) {
        super(id, name);
    }

    @Override
    public void borrowBook(Book book) {
        if (getBorrowedCount() >= LIMIT) {
            System.out.println("Premium limit reached (5 books).");
            return;
        }
        super.borrowBook(book);
    }
}