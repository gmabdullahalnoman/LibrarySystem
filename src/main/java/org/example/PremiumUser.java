package org.example;

public class PremiumUser extends User {

    public PremiumUser(int id, String name) {
        super(id, name);
        setBorrowLimit(5);
    }
}
/*    @Override
    public void borrowBook(Book book) {
        if (getBorrowedCount() >= LIMIT) {
            System.out.println("Premium limit reached (5 books).");
            return;
        }
        super.borrowBook(book);
    }
} */