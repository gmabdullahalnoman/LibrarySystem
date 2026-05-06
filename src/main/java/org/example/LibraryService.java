package org.example;

import java.util.ArrayList;

public class LibraryService implements LibraryOperations {

    private ArrayList<Book> books;

    public LibraryService() {
        this.books = new ArrayList<>();
    }
    private User findUserById(ArrayList<User> users, int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
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
        System.out.println( book.getTitle() + " by " +book.getAuthor() + " borrowed successfully.");
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

        System.out.println(book.getTitle() + " by " +book.getAuthor() + " returned successfully.");
    }
    @Override
    public void updateBook(int id, String title, String author, int quantity, User user) {

        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied. Only Admin can update books.");
            return;
        }

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!title.isEmpty()) book.setTitle(title);
        if (!author.isEmpty()) book.setAuthor(author);
        if (quantity >= 0) book.setQuantity(quantity);

        System.out.println("Book updated successfully.");
    }
    @Override
    public void deleteBook(int id, User user) {

        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied. Only Admin can delete books.");
            return;
        }

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        books.remove(book);
        System.out.println("Book deleted successfully.");
    }
    public void approveUser(int userId, ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.approve();
        System.out.println("User approved successfully.");
    }

    public void rejectUser(int userId, ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.reject();
        System.out.println("User rejected.");
    }
    public void approvePremium(int userId, ArrayList<User> users, User admin) {
        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null || !user.isPremiumRequested()) {
            System.out.println("No request found.");
            return;
        }

        user.setBorrowLimit(5);
        user.clearPremiumRequest();

        System.out.println("User upgraded to Premium.");
    }

    public void setUserBlock(int userId, boolean block, ArrayList<User> users, User admin) {
        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.setBlocked(block);
        System.out.println(block ? "User blocked." : "User unblocked.");
    }

    public void updateUserLimit(int userId, int limit, ArrayList<User> users, User admin) {
        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        user.setBorrowLimit(limit);
        System.out.println("Borrow limit updated.");
    }

    public void deleteUser(int userId, ArrayList<User> users, User admin) {
        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        users.remove(user);
        System.out.println("User deleted.");
    }

}