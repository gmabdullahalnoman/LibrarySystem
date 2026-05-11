package org.example;

import java.util.ArrayList;

public interface UserOperations {

    void approveUser(int userId, ArrayList<User> users, User admin);

    void rejectUser(int userId, ArrayList<User> users, User admin);

    void approvePremium(int id, ArrayList<User> users);

    void setUserBlock(int userId, boolean block,
                      ArrayList<User> users, User admin);

    void updateUserLimit(int userId, int limit,
                         ArrayList<User> users, User admin);

    void deleteUser(int id, ArrayList<User> users, User admin);

    void displayUsers(ArrayList<User> users);

    boolean displayActivationRequests(ArrayList<User> users);

    boolean displayPremiumRequests(ArrayList<User> users);

    boolean displayBlockedUsers(ArrayList<User> users);
}