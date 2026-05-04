package org.example;

public class Book {

    private static int idCounter = 1;

    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrowBook() {
        isAvailable = false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return id + " | " + title + " by " + author + " | " +
                (isAvailable ? "Available" : "Borrowed");
    }
}