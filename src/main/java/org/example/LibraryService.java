package org.example;

import org.example.exception.*;
import java.util.ArrayList;

public class LibraryService implements LibraryOperations {

    private ArrayList<Book> books;

    // NEW
    private ArrayList<Transaction> transactions;

    public LibraryService() {

        this.books = new ArrayList<>();

        this.transactions = new ArrayList<>();
    }

    private Book findBookById(int id) {

        for (Book book : books) {

            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    // NEW
    private Transaction findActiveTransaction(User user,
                                              Book book) {

        for (Transaction transaction : transactions) {

            boolean sameUser =
                    transaction.getUsername()
                            .equalsIgnoreCase(
                                    user.getUsername()
                            );

            boolean sameBook =
                    transaction.getBookTitle()
                            .equalsIgnoreCase(
                                    book.getTitle()
                            );

            if (sameUser &&
                    sameBook &&
                    !transaction.isReturned()) {

                return transaction;
            }
        }

        return null;
    }

    @Override
    public void addBook(Book newBook,
                        User user) {

        if (!(user instanceof AdminUser)) {

            throw new AccessDeniedException(
                    "Access denied."
            );
        }

        for (Book book : books) {

            if (book.getTitle()
                    .equalsIgnoreCase(newBook.getTitle())
                    &&
                    book.getAuthor()
                            .equalsIgnoreCase(newBook.getAuthor())) {

                for (int i = 0;
                     i < newBook.getQuantity();
                     i++) {

                    book.returnBook();
                }

                System.out.println(
                        "Stock updated: " +
                                book.getQuantity()
                );

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
    public void borrowBook(int id,
                           User user) {

        Book book = findBookById(id);

        if (book == null) {

            throw new BookNotFoundException(
                    "Book not found."
            );
        }

        if (book.getQuantity() <= 0) {

            throw new InvalidOperationException(
                    "Out of stock."
            );
        }

        int before = user.getBorrowedCount();

        user.borrowBook(book);

        int after = user.getBorrowedCount();

        if (before == after) {

            throw new InvalidOperationException(
                    "Borrow limit reached."
            );
        }

        book.borrowBook();

        // NEW TRANSACTION
        transactions.add(
                new Transaction(
                        user.getUsername(),
                        book.getTitle()
                )
        );

        System.out.println(
                "Borrowed: " +
                        book.getTitle()
        );
    }

    @Override
    public void returnBook(int id,
                           User user) {

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

        // UPDATE TRANSACTION
        Transaction transaction =
                findActiveTransaction(user, book);

        if (transaction != null) {
            transaction.markReturned();
        }

        System.out.println(
                "Returned: " +
                        book.getTitle()
        );
    }

    @Override
    public void updateBook(int id,
                           String title,
                           String author,
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

        System.out.println("Book details updated.");
    }
    @Override
    public void adjustStock(int id,
                            int amount,
                            boolean addStock,
                            String reason,
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

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (!addStock && amount > book.getQuantity()) {
            System.out.println("Cannot remove more than available stock.");
            return;
        }

        if (addStock) {

            book.setQuantity(
                    book.getQuantity() + amount
            );

        } else {

            book.setQuantity(
                    book.getQuantity() - amount
            );
        }

        System.out.println("\n===== Stock Adjustment =====");

        System.out.println(
                "Book: " + book.getTitle()
        );

        System.out.println(
                "Action: " +
                        (addStock ? "ADD" : "REMOVE")
        );

        System.out.println(
                "Amount: " + amount
        );

        System.out.println(
                "Reason: " + reason
        );

        System.out.println(
                "New Stock: " +
                        book.getQuantity()
        );
    }

    @Override
    public void deleteBook(int id,
                           User user,
                           ArrayList<User> users) {

        if (!(user instanceof AdminUser)) {
            throw  new AccessDeniedException("Access denied.");
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

    // NEW
    public void displayAllTransactions() {

        System.out.println(
                "\n===== All Transactions ====="
        );

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transaction history."
            );

            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    // NEW
    public void displayUserTransactions(User user) {

        System.out.println(
                "\n===== My Transaction History ====="
        );

        boolean found = false;

        for (Transaction transaction : transactions) {

            if (transaction.getUsername()
                    .equalsIgnoreCase(
                            user.getUsername()
                    )) {

                System.out.println(transaction);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No transaction history."
            );
        }
    }

    public boolean hasBooks() {
        return !books.isEmpty();
    }
}