package org.example;

public class StudentUser extends User {

    public StudentUser(int id, String name, String username, String password) {
        super(id, name, username, password);
        setBorrowLimit(2);
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