package org.example;

import java.util.ArrayList;

public interface AuthOperations {

    void registerUser(ArrayList<User> users,
                      User user);

    User loginUser(ArrayList<User> users,
                   String username,
                   String password);

    boolean usernameExists(ArrayList<User> users,
                           String username);
}