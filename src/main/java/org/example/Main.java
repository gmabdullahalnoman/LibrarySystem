package org.example;

public class Main {

    public static void main(String[] args) {

        LibraryOperations library = new LibraryService();

        User user1 = new StudentUser(1, "Abdullah");

        Book book1 = new Book(101, "Clean Code", "Robert C. Martin");
        Book book2 = new Book(102, "Effective Java", "Joshua Bloch");

        library.addBook(book1);
        library.addBook(book2);

        System.out.println("\nAll Books:");
        library.displayBooks();

        System.out.println("\nBorrow Book:");
        library.borrowBook(101, user1);

        System.out.println("\nUser Borrowed Books:");
        user1.showBorrowedBooks();

        System.out.println("\nAll Books After Borrow:");
        library.displayBooks();

        System.out.println("\nReturn Book:");
        library.returnBook(101, user1);

        System.out.println("\nFinal Books State:");
        library.displayBooks();
    }
}