package org.example;

import java.util.ArrayList;

public class User extends Person {

    public enum Status {
        PENDING,
        ACTIVE,
        REJECTED
    }

    private ArrayList<Book> borrowedBooks;
    private Status status;
    private boolean isBlocked;
    private int borrowLimit;
    private boolean premiumRequested;

    public User(int userId, String name) {
        super(userId, name);
        this.borrowedBooks = new ArrayList<>();
        this.status = Status.PENDING;
        this.isBlocked = false;
        this.borrowLimit = 2;
        this.premiumRequested = false;
    }

    public void borrowBook(Book book) {
        if (isBlocked) {
            System.out.println("Your account is blocked.");
            return;
        }

        if (status != Status.ACTIVE) {
            System.out.println("Your account is not active.");
            return;
        }

        if (borrowedBooks.contains(book)) {
            System.out.println("You already borrowed this book.");
            return;
        }

        if (borrowedBooks.size() >= borrowLimit) {
            System.out.println("Borrow limit reached.");
            return;
        }

        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void showBorrowedBooks() {
        System.out.println("Borrowed: " + borrowedBooks.size() + "/" + borrowLimit);
        System.out.println("Status: " + status + (isBlocked ? " (Blocked)" : ""));

        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }

        for (Book book : borrowedBooks) {
            System.out.println(book.getId() + " | " + book.getTitle() + " by " + book.getAuthor());
        }
    }

    public int getBorrowedCount() {
        return borrowedBooks.size();
    }

    public boolean hasBorrowedBook(Book book) {
        for (Book b : borrowedBooks) {
            if (b.getId() == book.getId()) return true;
        }
        return false;
    }

    // Status control
    public Status getStatus() {
        return status;
    }

    public void approve() {
        this.status = Status.ACTIVE;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    // Block control
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    // Borrow limit
    public void setBorrowLimit(int limit) {
        this.borrowLimit = limit;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    // Premium request
    public void requestPremium() {
        if (premiumRequested) {
            System.out.println("Already requested.");
            return;
        }
        premiumRequested = true;
        System.out.println("Premium request sent.");
    }

    public boolean isPremiumRequested() {
        return premiumRequested;
    }

    public void clearPremiumRequest() {
        premiumRequested = false;
    }
}