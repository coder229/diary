package com.github.coder229.diary.user;

import org.springframework.data.annotation.Id;

/**
 * Created by scott on 9/5/2015.
 */
class User {
    @Id
    String id
    String username
    String password
    String email
    boolean enabled

    String accessToken
}
