package com.zynotic.studios.quadsquad.questlog.utils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

public class SessionManager {

    @NotNull
    private boolean isLoggedIn = false;

    @NotBlank
    @Length(min = 4, max = 30)
    @UniqueElements
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void login(@NotBlank @Length(min = 4, max = 30) @UniqueElements @Pattern(regexp = "^[a-z0-9]+$") String username) {
        isLoggedIn = true;
        this.username = username;
    }

    public void logout() {
        isLoggedIn = false;
        this.username = null;
    }
}

