package com.github.coder229.diary.security

import com.github.coder229.diary.user.User
import com.github.coder229.diary.user.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.ZoneId
/**
 * Created by scott on 9/18/2015.
 */
@Service
class TokenHandler {

    @Value('${token.secret}')
    String secret

    @Autowired
    UserRepository userRepository

    User parseUserFromToken(String token) {
        def body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
        String username = body.getSubject()
        userRepository.findByUsername(username)
    }

    String createTokenForUser(User user, LocalDateTime dateTime) {
        Date expiration = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Jwts.builder()
            .setSubject(user.username)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }
}
