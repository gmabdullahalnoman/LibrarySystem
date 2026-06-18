package org.example.interfaces;

import org.example.model.User;
import org.example.enums.UserType;

import java.util.ArrayList;

public interface AuthOperations {

    void registerUser(ArrayList<User> users,
                      User user);

    User loginUser(ArrayList<User> users,
                   String username,
                   String password);

    boolean usernameExists(ArrayList<User> users,
                           String username);

    User createUser(UserType type,
                    int id,
                    String name,
                    String username,
                    String password);
}