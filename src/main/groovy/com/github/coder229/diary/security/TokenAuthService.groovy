package com.github.coder229.diary.security
import com.github.coder229.diary.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/**
 * Created by scott on 9/18/2015.
 */
@Service
class TokenAuthService {
    static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN"

    @Autowired
    TokenHandler tokenHandler

    void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        User user = authentication.getDetails();

        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME)
        if (token != null) {
            User user = tokenHandler.parseUserFromToken(token)
            new UserAuthentication(user)
        }
    }
}
