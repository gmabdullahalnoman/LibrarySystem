package org.example;

import java.util.ArrayList;

public class User extends Person {

    private ArrayList<Book> borrowedBooks;

    public User(int userId, String name) {
        super(userId, name);
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            borrowedBooks.add(book);
        }
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void showBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }

        for (Book book : borrowedBooks) {
            System.out.println(book);
        }
    }
}