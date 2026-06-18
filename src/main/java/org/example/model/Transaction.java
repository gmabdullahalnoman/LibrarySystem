package org.example.model;

import java.time.LocalDateTime;

public class Transaction {

    private final String username;
    private final String bookTitle;

    private final LocalDateTime borrowTime;

    private LocalDateTime returnTime;

    private boolean returned;

    public Transaction(String username,
                       String bookTitle) {

        this.username = username;
        this.bookTitle = bookTitle;

        this.borrowTime = LocalDateTime.now();

        this.returned = false;
    }

    public void markReturned() {

        this.returnTime = LocalDateTime.now();

        this.returned = true;
    }

    public String getUsername() {
        return username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public boolean isReturned() {
        return returned;
    }

    @Override
    public String toString() {

        return
                "User: " + username +
                        " | Book: " + bookTitle +
                        " | Borrowed: " + borrowTime +
                        " | Returned: " +
                        (returned
                                ? returnTime
                                : "NOT RETURNED");
    }
}