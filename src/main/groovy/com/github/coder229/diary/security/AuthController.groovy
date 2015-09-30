package com.github.coder229.diary.security
import com.github.coder229.diary.user.User
import com.github.coder229.diary.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

import java.time.LocalDateTime
/**
 * Created by scott on 9/8/2015.
 */
@RestController
class AuthController {

    @Autowired
    TokenHandler tokenHandler

    @Autowired
    UserRepository userRepository

    @RequestMapping(value = '/login', method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    AuthResponse login(@RequestBody AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.username)
        String accessToken = tokenHandler.createTokenForUser(user, LocalDateTime.now().plusDays(1))
        user.accessToken = accessToken
        userRepository.save(user)
        new AuthResponse(
                username: authRequest.username,
                accessToken: accessToken)
    }

    @RequestMapping(value = '/userinfo', method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    AuthResponse getUserInfo() {
        UserAuthentication authentication = SecurityContextHolder.getContext().authentication
        new AuthResponse(
                username: authentication.user.username,
                accessToken: authentication.user.accessToken)
    }
}
