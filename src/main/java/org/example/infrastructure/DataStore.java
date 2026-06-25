package org.example.infrastructure;

import org.example.model.Book;
import org.example.model.Transaction;
import org.example.model.User;

import java.util.ArrayList;

public class DataStore {

    private final ArrayList<User> users;
    private final ArrayList<Book> books;
    private final ArrayList<Transaction> transactions;

    public DataStore() {

        users = new ArrayList<>();
        books = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}