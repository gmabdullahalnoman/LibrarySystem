package org.example;

import java.util.ArrayList;

public class UserService implements UserOperations {

    private User findUserById(ArrayList<User> users, int id) {

        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void approveUser(int userId, ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (!user.isActivationRequested()) {
            System.out.println("User did not request activation.");
            return;
        }

        user.approve();

        System.out.println("User approved.");
    }

    @Override
    public void rejectUser(int userId, ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            return;
        }

        User u = findUserById(users, userId);

        if (u == null) {
            return;
        }

        u.reject();

        System.out.println("User rejected.");
    }

    @Override
    public void approvePremium(int id, ArrayList<User> users) {

        User u = findUserById(users, id);

        if (u == null || !u.isPremiumRequested()) {
            return;
        }

        u.setBorrowLimit(5);
        u.clearPremiumRequest();

        System.out.println("Upgraded.");
    }

    @Override
    public void setUserBlock(int userId, boolean block,
                             ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (user instanceof AdminUser) {
            System.out.println("Admin accounts cannot be blocked.");
            return;
        }

        user.setBlocked(block);

        System.out.println(block ?
                "User blocked successfully." :
                "User unblocked successfully.");
    }

    @Override
    public void updateUserLimit(int userId, int limit,
                                ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User user = findUserById(users, userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (user.getBorrowLimit() < 5) {
            System.out.println("User is not premium.");
            return;
        }

        if (user.getStatus() != User.Status.ACTIVE) {
            System.out.println("User is not active.");
            return;
        }

        if (limit < 5) {
            System.out.println("Premium limit cannot be below 5.");
            return;
        }

        user.setBorrowLimit(limit);

        System.out.println("Premium limit updated.");
    }

    @Override
    public void deleteUser(int id, ArrayList<User> users, User admin) {

        if (!(admin instanceof AdminUser)) {
            System.out.println("Access denied.");
            return;
        }

        User u = findUserById(users, id);

        if (u == null) {
            System.out.println("User not found.");
            return;
        }

        // cannot delete own account
        if (u == admin) {
            System.out.println("Admin cannot delete own account.");
            return;
        }

        // cannot delete admin accounts
        if (u instanceof AdminUser) {
            System.out.println("Admin accounts cannot be deleted.");
            return;
        }

        // user currently borrowing books
        if (u.getBorrowedCount() > 0) {
            System.out.println("User has borrowed books.");
            return;
        }

        // pending activation request
        if (u.isActivationRequested()) {
            System.out.println("User has pending activation request.");
            return;
        }

        // pending premium request
        if (u.isPremiumRequested()) {
            System.out.println("User has pending premium request.");
            return;
        }

        users.remove(u);

        System.out.println("User deleted successfully.");
    }

    @Override
    public void displayUsers(ArrayList<User> users) {

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {

            String role =
                    (user instanceof AdminUser) ? "Admin" : "Student";

            if (user instanceof AdminUser) {

                System.out.println(
                        "ID: " + user.getId() +
                                " | Name: " + user.getName() +
                                " | Username: " + user.getUsername() +
                                " | Role: " + role
                );

            } else {

                System.out.println(
                        "ID: " + user.getId() +
                                " | Name: " + user.getName() +
                                " | Username: " + user.getUsername() +
                                " | Role: " + role +
                                " | Status: " + user.getStatus() +
                                (user.isBlocked() ? " | BLOCKED" : "") +
                                " | Limit: " + user.getBorrowLimit()
                );
            }
        }
    }

    @Override
    public boolean displayActivationRequests(ArrayList<User> users) {

        boolean found = false;

        System.out.println("===== Activation Requests =====");

        for (User u : users) {

            if (!(u instanceof AdminUser)
                    && u.isActivationRequested()
                    && u.getStatus() == User.Status.PENDING) {

                System.out.println(
                        "ID: " + u.getId() +
                                " | Name: " + u.getName() +
                                " | Status: " + u.getStatus()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No activation requests found.");
        }

        return found;
    }

    @Override
    public boolean displayPremiumRequests(ArrayList<User> users) {

        boolean found = false;

        System.out.println("===== Premium Requests =====");

        for (User u : users) {

            if (u.isPremiumRequested()) {

                System.out.println(
                        "ID: " + u.getId() +
                                " | Name: " + u.getName()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No premium requests.");
        }

        return found;
    }

    @Override
    public boolean displayBlockedUsers(ArrayList<User> users) {

        boolean found = false;

        System.out.println("===== Blocked Users =====");

        for (User u : users) {

            if (u.isBlocked()) {

                System.out.println(
                        "ID: " + u.getId() +
                                " | Name: " + u.getName()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No blocked users.");
        }

        return found;
    }
}