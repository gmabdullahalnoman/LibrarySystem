package org.example;

public class StudentUser extends User {

    public StudentUser(int id, String name) {
        super(id, name);
        setBorrowLimit(2); // moved here
    }
}
/*
    @Override
    public void borrowBook(Book book) {
        if (getBorrowedCount() >= LIMIT) {
            System.out.println("Student limit reached (2 books).");
            return;
        }
        super.borrowBook(book);
    }
} */