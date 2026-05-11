package org.example;

import java.util.ArrayList;

public class LibraryService implements LibraryOperations {

    private ArrayList<Book> books;

    public LibraryService() {
        this.books = new ArrayList<>();
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
    public void addBook(Book newBook, User user) {

        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        for (Book book : books) {

            if (book.getTitle().equalsIgnoreCase(newBook.getTitle()) &&
                    book.getAuthor().equalsIgnoreCase(newBook.getAuthor())) {

                for (int i = 0; i < newBook.getQuantity(); i++) {
                    book.returnBook();
                }

                System.out.println("Stock updated: " + book.getQuantity());
                return;
            }
        }

        books.add(newBook);

        System.out.println("Book added.");
    }

    @Override
    public void displayBooks() {

        if (books.isEmpty()) {
            System.out.println("No books.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Override
    public void borrowBook(int id, User user) {

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getQuantity() <= 0) {
            System.out.println("Out of stock.");
            return;
        }

        int before = user.getBorrowedCount();

        user.borrowBook(book);

        int after = user.getBorrowedCount();

        if (before == after) {
            return;
        }

        book.borrowBook();

        System.out.println("Borrowed: " + book.getTitle());
    }

    @Override
    public void returnBook(int id, User user) {

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!user.hasBorrowedBook(book)) {
            System.out.println("Not your book.");
            return;
        }

        user.returnBook(book);

        book.returnBook();

        System.out.println("Returned: " + book.getTitle());
    }

    @Override
    public void updateBook(int id,
                           String title,
                           String author,
                           int quantity,
                           User user) {

        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!title.isEmpty()) {
            book.setTitle(title);
        }

        if (!author.isEmpty()) {
            book.setAuthor(author);
        }

        if (quantity >= 0) {
            book.setQuantity(quantity);
        }

        System.out.println("Updated.");
    }

    @Override
    public void deleteBook(int id,
                           User user,
                           ArrayList<User> users) {

        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getQuantity() > 0) {
            System.out.println("Stock not zero.");
            return;
        }

        for (User u : users) {

            if (u.hasBorrowedBook(book)) {
                System.out.println("Book is borrowed.");
                return;
            }
        }

        books.remove(book);

        System.out.println("Book deleted.");
    }

    public boolean hasBooks() {
        return !books.isEmpty();
    }
}