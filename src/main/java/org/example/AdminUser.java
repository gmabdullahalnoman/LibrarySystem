package org.example;

public class AdminUser extends User {

    public AdminUser(int id, String name) {
        super(id, name);
        // Admin auto active
        approve();
        // practically unlimited
        setBorrowLimit(Integer.MAX_VALUE);
    }
}