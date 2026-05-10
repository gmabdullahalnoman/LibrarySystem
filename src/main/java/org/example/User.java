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
    // NEW tracking
    private int totalBorrowed;
    private int totalReturned;
    private boolean activationRequested;
    private String username;
    private String password;

    public User(int userId, String name, String username, String password) {
        super(userId, name);
        this.borrowedBooks = new ArrayList<>();
        this.status = Status.PENDING;
        this.isBlocked = false;
        this.borrowLimit = 2;
        this.premiumRequested = false;

        this.totalBorrowed = 0;
        this.totalReturned = 0;
        this.activationRequested = false;
        this.username = username;
        this.password = password;
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
        totalBorrowed++; // track
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            totalReturned++; // track
        }
    }

    public void showBorrowedBooks() {
        System.out.println("Borrowed: " + borrowedBooks.size() + "/" + borrowLimit);
        System.out.println("Status: " + status + (isBlocked ? " (Blocked)" : ""));

        // hints
        if (status == Status.PENDING && !activationRequested) {
            System.out.println(">> You can request account activation.");
        }

        if (premiumRequested) {
            System.out.println(">> Premium request pending approval.");
        }

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
        this.activationRequested = false;
    }

    public void reject() {
        this.status = Status.REJECTED;
        this.activationRequested = false;
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

    // Premium request (with eligibility)
    public void requestPremium() {

        if (premiumRequested) {
            System.out.println("Already requested.");
            return;
        }

        if (!(totalBorrowed > 0 && totalReturned > 0)) {
            System.out.println("You must borrow and return at least 1 book before requesting premium.");
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

    // Activation request
    public void requestActivation() {

        if (activationRequested) {
            System.out.println("Activation request already pending.");
            return;
        }

        activationRequested = true;
        System.out.println("Activation request sent to admin.");
    }
    public void clearActivationRequest() {
        activationRequested = false;
    }

    public boolean isActivationRequested() {
        return activationRequested;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}