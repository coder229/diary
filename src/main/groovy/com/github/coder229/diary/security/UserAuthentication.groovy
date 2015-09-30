package com.github.coder229.diary.security

import com.github.coder229.diary.user.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

/**
 * Created by scott on 9/19/2015.
 */
class UserAuthentication implements Authentication {

    User user
    boolean authenticated = true

    UserAuthentication(User user) {
        this.user = user
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        user.getAuthorities()
    }

    @Override
    Object getCredentials() {
        user.password
    }

    @Override
    Object getDetails() {
        user
    }

    @Override
    Object getPrincipal() {
        user.getUsername()
    }

    @Override
    boolean isAuthenticated() {
        authenticated
    }

    @Override
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated
    }

    @Override
    String getName() {
        user.username
    }
}
