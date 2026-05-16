package org.example;

import java.util.ArrayList;

public class AuthService implements AuthOperations {

    @Override
    public void registerUser(ArrayList<User> users,
                             User user) {

        if (user == null) {

            System.out.println("Invalid user.");
            return;
        }
        if (usernameExists(users, user.getUsername())) {
            System.out.println("Username already exists.");
            return;
        }

        users.add(user);

        System.out.println("Registered successfully.");

        System.out.println(
                "Username: " +
                        user.getUsername()
        );
    }

    @Override
    public User loginUser(ArrayList<User> users,
                          String username,
                          String password) {

        User user =
                findUserByUsername(
                        users,
                        username
                );

        if (user == null) {
            System.out.println("User not found.");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return null;
        }

        System.out.println(
                "Welcome " +
                        user.getName()
        );

        return user;
    }

    @Override
    public boolean usernameExists(ArrayList<User> users,
                                  String username) {

        return findUserByUsername(
                users,
                username
        ) != null;
    }

    @Override
    public User createUser(int type,
                           int id,
                           String name,
                           String username,
                           String password) {

        if (type == 1) {

            return new StudentUser(
                    id,
                    name,
                    username,
                    password
            );
        }

        if (type == 2) {
            return new AdminUser(
                    id,
                    name,
                    username,
                    password
            );
        }
        return null;
    }
    // PRIVATE HELPERS

    private User findUserByUsername(ArrayList<User> users,
                                    String username) {

        for (User user : users) {

            if (user.getUsername()
                    .equalsIgnoreCase(username)) {

                return user;
            }
        }

        return null;
    }
}