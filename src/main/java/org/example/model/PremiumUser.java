package org.example.model;

public class PremiumUser extends User {

    public PremiumUser(int id, String name, String username, String password) {
        super(id, name, username, password);
        setBorrowLimit(5);
    }
}