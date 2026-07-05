package org.example.model;

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
    private boolean activationRequested;
    private String rejectionReason;
    private int totalBorrowed;
    private int totalReturned;
    private final String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    public User(int userId,
                String name,
                String username,
                String password) {
        super(userId, name);
        this.borrowedBooks = new ArrayList<>();
        this.status = Status.PENDING;
        this.isBlocked = false;
        this.borrowLimit = 2;
        this.premiumRequested = false;

        this.activationRequested = false;
        this.rejectionReason = "";
        this.totalBorrowed = 0;
        this.totalReturned = 0;
        this.username = username;
        this.password = password;
    }
    // BOOK OPERATIONS
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

        totalBorrowed++;
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            totalReturned++;
        }
    }

    public boolean hasBorrowedBook(Book book) {

        for (Book b : borrowedBooks) {

            if (b.getId() == book.getId()) {
                return true;
            }
        }
        return false;
    }
    public int getBorrowedCount() {
        return borrowedBooks.size();
    }
    // DISPLAY

    public void showBorrowedBooks() {

        System.out.println("\n===== My Account =====");

        System.out.println(
                "Status: " + status +
                        (isBlocked ? " (BLOCKED)" : "")
        );

        System.out.println(
                "Borrow Limit: " +
                        borrowedBooks.size() +
                        "/" +
                        borrowLimit
        );

        System.out.println(
                "Total Borrowed History: " +
                        totalBorrowed
        );

        System.out.println(
                "Total Returned History: " +
                        totalReturned
        );

        if (status == Status.PENDING && !activationRequested) {

            System.out.println(
                    ">> You can request activation."
            );
        }
        if (status == Status.REJECTED) {

            System.out.println(
                    "Rejection Reason: " +
                            rejectionReason
            );
        }

        if (activationRequested) {

            System.out.println(
                    ">> Activation request pending."
            );
        }

        if (premiumRequested) {

            System.out.println(
                    ">> Premium request pending."
            );
        }
        System.out.println("\n===== Borrowed Books =====");

        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }

        for (Book book : borrowedBooks) {

            System.out.println(
                    book.getId() +
                            " | " +
                            book.getTitle() +
                            " by " +
                            book.getAuthor()
            );
        }
    }

    // STATUS

    public Status getStatus() {
        return status;
    }

    public void approve() {

        this.status = Status.ACTIVE;
        this.activationRequested = false;
        this.rejectionReason = "";
    }

    public void reject(String reason) {

        this.status = Status.REJECTED;
        this.activationRequested = false;
        this.rejectionReason = reason;
    }
    public void resubmitActivationRequest() {

        if (status != Status.REJECTED) {
            System.out.println("Account is not rejected.");
            return;
        }

        status = Status.PENDING;
        activationRequested = true;
        rejectionReason = "";

        System.out.println("Activation request resubmitted.");
    }

    // BLOCK
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    // BORROW LIMIT
    public void setBorrowLimit(int limit) {
        this.borrowLimit = limit;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    // PREMIUM
    public void requestPremium() {

        if (premiumRequested) {
            System.out.println("Already requested.");
            return;
        }

        if (!(totalBorrowed > 0 && totalReturned > 0)) {

            System.out.println(
                    "Borrow and return at least 1 book first."
            );

            return;
        }

        premiumRequested = true;

        System.out.println(
                "Premium request sent."
        );
    }

    public boolean isPremiumRequested() {
        return premiumRequested;
    }

    public void clearPremiumRequest() {
        premiumRequested = false;
    }

    // ACTIVATION
    public void requestActivation() {

        if (activationRequested) {

            System.out.println(
                    "Activation request already pending."
            );
            return;
        }

        activationRequested = true;
        System.out.println(
                "Activation request sent to admin."
        );
    }
    public String getRejectionReason() {
        return rejectionReason;
    }

    public boolean isActivationRequested() {
        return activationRequested;
    }
    public void clearActivationRequest() {
        activationRequested = false;
    }
    // AUTH
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    // HISTORY
    public int getTotalBorrowed() {
        return totalBorrowed;
    }
    public int getTotalReturned() {
        return totalReturned;
    }
}