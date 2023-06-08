package com.example.mnemory.User;

public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
        // Private constructor to prevent instantiation from outside the class
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}
