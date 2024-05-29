package com.zynotic.studios.quadsquad.questlog.utils;

import com.zynotic.studios.quadsquad.questlog.entities.User;

public class SessionManager {

    private boolean isSignedIn = false;

    private User user;

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public User getUser() {
        return user;
    }

    public void signInSuccess(User user) {
        isSignedIn = true;
        this.user = user;
    }

    public void signOut() {
        isSignedIn = false;
        this.user = null;
    }
}

