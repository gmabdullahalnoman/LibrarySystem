package org.example.model;

public class Book {

    private static int idCounter = 1;

    private int id;
    private String title;
    private String author;
    private int quantity; // stock

    public Book(String title, String author, int quantity) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return quantity > 0; // derived
    }

    public void borrowBook() {
        if (quantity > 0) {
            quantity--; // decrease stock
        }
    }

    public void returnBook() {
        quantity++; // increase stock
    }

    @Override
    public String toString() {
        return id + " | " + title + " by " + author + " | Stock: " + quantity;
    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return id == book.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}