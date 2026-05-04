package org.example;

import java.util.ArrayList;

public class LibraryService implements LibraryOperations {

    private ArrayList<Book> books;

    public LibraryService() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully.");
    }

    @Override
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void borrowBook(int id, User user) {
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return;
        }
        int before = user.getBorrowedCount();
        user.borrowBook(book);
        int after = user.getBorrowedCount();
        if (before == after) {
            return;
        }
        book.borrowBook();
        System.out.println("Book borrowed successfully.");
    }

    @Override
    public void returnBook(int id, User user) {
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        book.returnBook();
        user.returnBook(book);

        System.out.println("Book returned successfully.");
    }
}