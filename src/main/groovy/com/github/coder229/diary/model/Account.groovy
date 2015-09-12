package com.github.coder229.diary.model;

import org.springframework.data.annotation.Id;

/**
 * Created by scott on 9/5/2015.
 */
class Account {
    @Id
    String id;
    String username;
    String password;
    boolean enabled;
}
