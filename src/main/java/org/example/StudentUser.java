package org.example;

public class StudentUser extends User {

    public StudentUser(int id,
                       String name,
                       String username,
                       String password) {

        super(
                id,
                name,
                username,
                password
        );

        setBorrowLimit(2);
    }
}