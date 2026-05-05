package org.example;

import java.util.ArrayList;

public class LibraryService implements LibraryOperations {

    private ArrayList<Book> books;

    public LibraryService() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book newBook, User user) {

        // RBAC check
        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied. Only Admin can add books.");
            return;
        }

        // check duplicate (title + author)
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(newBook.getTitle()) &&
                    book.getAuthor().equalsIgnoreCase(newBook.getAuthor())) {

                // merge stock
                int updatedQty = book.getQuantity() + newBook.getQuantity();
                // need setter OR loop increment
                for (int i = 0; i < newBook.getQuantity(); i++) {
                    book.returnBook(); // reuse method to increase
                }

                System.out.println("Book already exists. Stock updated to: " + book.getQuantity());
                return;
            }
        }

        // new book
        books.add(newBook);
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

        // stock check instead of boolean
        if (book.getQuantity() <= 0) {
            System.out.println("Book out of stock.");
            return;
        }

        int before = user.getBorrowedCount();
        user.borrowBook(book);
        int after = user.getBorrowedCount();

        // limit reached
        if (before == after) {
            return;
        }

        // decrease stock only after success
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

        if (!user.hasBorrowedBook(book)) {
            System.out.println("You didn't borrow this book.");
            return;
        }

        user.returnBook(book);
        book.returnBook();

        System.out.println("Book returned successfully.");
    }

    /*
    *# Old methods - Safe to delete
    private boolean userHasBook(User user, Book book) {
        return user.getBorrowedCount() > 0 &&
                userHasSpecificBook(user, book);
    }

    private boolean userHasSpecificBook(User user, Book book) {
        for (Book b : userBorrowedList(user)) {
            if (b.getId() == book.getId()) return true;
        }
        return false;
    }

    private java.util.List<Book> userBorrowedList(User user) {
        try {
            java.lang.reflect.Field field = User.class.getDeclaredField("borrowedBooks");
            field.setAccessible(true);
            return (java.util.List<Book>) field.get(user);
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
     */
}