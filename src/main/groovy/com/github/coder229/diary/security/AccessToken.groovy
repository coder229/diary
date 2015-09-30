package com.github.coder229.diary.security

import org.springframework.data.annotation.Id

/**
 * Created by scott on 9/17/2015.
 */
class AccessToken {
    @Id
    String id
    String username
    String accessToken
    Date expires
}
