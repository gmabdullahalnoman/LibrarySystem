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

    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
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

        if (before == after) return;

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
    public void updateBook(int id, String title, String author, int quantity, User user) {
        if (!(user instanceof AdminUser)) {
            System.out.println("Access denied.");
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

        System.out.println("Updated.");
    }

    @Override
    public void deleteBook(int id, User user, ArrayList<User> users) {
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

    @Override
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

        // must request activation first
        if (!user.isActivationRequested()) {
            System.out.println("User did not request activation.");
            return;
        }

        user.approve();
        System.out.println("User approved.");
    }

    @Override
    public void rejectUser(int userId, ArrayList<User> users, User admin) {
        if (!(admin instanceof AdminUser)) return;

        User u = findUserById(users, userId);
        if (u == null) return;

        u.reject();
        System.out.println("User rejected.");
    }

    // EXTRA methods (not in interface)
    public void approvePremium(int id, ArrayList<User> users) {
        User u = findUserById(users, id);
        if (u == null || !u.isPremiumRequested()) return;

        u.setBorrowLimit(5);
        u.clearPremiumRequest();
        System.out.println("Upgraded.");
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

        // cannot block admin
        if (user instanceof AdminUser) {
            System.out.println("Admin accounts cannot be blocked.");
            return;
        }

        user.setBlocked(block);

        System.out.println(block ?
                "User blocked successfully." :
                "User unblocked successfully.");
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
        // only ACTIVE premium users
        if (user.getBorrowLimit() < 5) {
            System.out.println("User is not a premium user.");
            return;
        }

        if (user.getStatus() != User.Status.ACTIVE) {
            System.out.println("User is not active.");
            return;
        }

        if (limit < 5) {
            System.out.println("Premium limit cannot be below 5.");
            return;
        }

        user.setBorrowLimit(limit);

        System.out.println("Premium user borrow limit updated.");
    }

    public void deleteUser(int id, ArrayList<User> users, User admin) {
        User u = findUserById(users, id);

        if (u == null) return;

        if (u.getBorrowedCount() > 0) {
            System.out.println("User has books.");
            return;
        }

        users.remove(u);
    }
    public void displayUsers(ArrayList<User> users) {

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {

            String role = (user instanceof AdminUser) ? "Admin" : "Student";

            // admin view
            if (user instanceof AdminUser) {

                System.out.println(
                        "ID: " + user.getId() +
                                " | Name: " + user.getName() +
                                " | Username: " + user.getUsername() +
                                " | Role: " + role
                );
            } else {

                System.out.println(
                        "ID: " + user.getId() +
                                " | Name: " + user.getName() +
                                " | Username: " + user.getUsername() +
                                " | Role: " + role +
                                " | Status: " + user.getStatus() +
                                (user.isBlocked() ? " | BLOCKED" : "") +
                                " | Limit: " + user.getBorrowLimit()
                );
            }
        }
    }
    public boolean displayActivationRequests(ArrayList<User> users) {

        boolean found = false;

        System.out.println("===== Activation Requests =====");

        for (User u : users) {

            if (!(u instanceof AdminUser)
                    && u.isActivationRequested()
                    && u.getStatus() == User.Status.PENDING) {

                System.out.println(
                        "ID: " + u.getId() +
                                " | Name: " + u.getName() +
                                " | Status: " + u.getStatus()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No activation requests found.");
        }

        return found;
    }
    public boolean displayPremiumRequests(ArrayList<User> users) {
        boolean found = false;

        System.out.println("===== Premium Requests =====");

        for (User u : users) {
            if (u.isPremiumRequested()) {
                System.out.println("ID: " + u.getId() + " | Name: " + u.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No premium requests.");
        }

        return found;
    }
    public boolean displayBlockedUsers(ArrayList<User> users) {
        boolean found = false;

        System.out.println("===== Blocked Users =====");

        for (User u : users) {
            if (u.isBlocked()) {
                System.out.println("ID: " + u.getId() + " | Name: " + u.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No blocked users.");
        }
        return  found;
    }
    public boolean hasBooks() {
        return !books.isEmpty();
    }
}