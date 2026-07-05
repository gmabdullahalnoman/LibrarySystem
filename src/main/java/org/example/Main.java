package org.example;

import org.example.menu.AdminMenuHandler;
import org.example.menu.AuthMenuHandler;
import org.example.menu.UserMenuHandler;
import org.example.model.AdminUser;
import org.example.model.User;
import org.example.service.AuthService;
import org.example.service.LibraryService;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void main() {

        LibraryService library = new LibraryService();
        UserService userService = new UserService();
        AuthService authService = new AuthService();

        AuthMenuHandler authMenu =
                new AuthMenuHandler(authService);

        AdminMenuHandler adminMenu =
                new AdminMenuHandler(library, userService);

        UserMenuHandler userMenu =
                new UserMenuHandler(library, authService);

        ArrayList<User> users = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        User currentUser = null;

        while (true) {

            if (currentUser == null) {

                currentUser =
                        authMenu.handleAuthMenu(users, sc);

                continue;
            }

            if (currentUser instanceof AdminUser) {

                currentUser =
                        adminMenu.handleAdminMenu(
                                currentUser,
                                users,
                                sc
                        );

            } else {

                currentUser =
                        userMenu.handleUserMenu(
                                currentUser,
                                sc
                        );
            }
        }
    }
}