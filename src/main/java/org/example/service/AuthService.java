package org.example.service;

import org.example.enums.UserType;
import org.example.exception.AuthenticationException;
import org.example.interfaces.AuthOperations;
import org.example.model.AdminUser;
import org.example.model.StudentUser;
import org.example.model.User;

import java.util.ArrayList;

public class AuthService implements AuthOperations {

    @Override
    public void registerUser(ArrayList<User> users,
                             User user) {

        if (user == null) {

            System.out.println("Invalid user.");
            return;
        }

        if (usernameExists(users,
                user.getUsername())) {

            System.out.println(
                    "Username already exists."
            );

            return;
        }

        users.add(user);

        System.out.println(
                "Registered successfully."
        );

        System.out.println(
                "Username: "
                        + user.getUsername()
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

            throw new AuthenticationException(
                    "User not found."
            );
        }

        if (!user.getPassword()
                .equals(password)) {

            throw new AuthenticationException(
                    "Incorrect password."
            );
        }

        System.out.println(
                "Welcome "
                        + user.getName()
        );

        return user;
    }

    @Override
    public boolean usernameExists(
            ArrayList<User> users,
            String username) {

        return findUserByUsername(
                users,
                username
        ) != null;
    }

    @Override
    public User createUser(UserType type,
                           int id,
                           String name,
                           String username,
                           String password) {

        return switch (type) {

            case STUDENT ->

                    new StudentUser(
                            id,
                            name,
                            username,
                            password
                    );

            case ADMIN ->

                    new AdminUser(
                            id,
                            name,
                            username,
                            password
                    );
        };
    }


    private User findUserByUsername(
            ArrayList<User> users,
            String username) {

        for (User user : users) {

            if (user.getUsername()
                    .equalsIgnoreCase(
                            username
                    )) {

                return user;
            }
        }

        return null;
    }
    @Override
    public void changePassword(User user,
                               String currentPassword,
                               String newPassword) {

        if (user == null) {
            System.out.println("Invalid user.");
            return;
        }

        if (!user.getPassword().equals(currentPassword)) {
            System.out.println("Current password is incorrect.");
            return;
        }

        if (currentPassword.equals(newPassword)) {
            System.out.println("New password cannot be same as current password.");
            return;
        }

        user.setPassword(newPassword);

        System.out.println("Password changed successfully.");
    }
    @Override
    public void forgotPassword(ArrayList<User> users,
                               String username,
                               String securityAnswer,
                               String newPassword) {

        User user = findUserByUsername(users, username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (user.getSecurityQuestion() == null ||
                user.getSecurityAnswer() == null) {

            System.out.println("Security question not set for this account.");
            return;
        }

        String storedAnswer =
                user.getSecurityAnswer().trim().toLowerCase();

        String inputAnswer =
                securityAnswer.trim().toLowerCase();

        if (!storedAnswer.equals(inputAnswer)) {
            System.out.println("Incorrect security answer.");
            return;
        }

        if (user.getPassword().equals(newPassword)) {
            System.out.println("New password cannot be same as old password.");
            return;
        }

        user.setPassword(newPassword);

        System.out.println("Password reset successfully.");
    }
}