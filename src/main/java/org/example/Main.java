package org.example;

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
                new UserMenuHandler(library);

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