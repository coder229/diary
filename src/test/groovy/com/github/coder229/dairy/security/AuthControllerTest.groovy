package com.github.coder229.dairy.security
import com.github.coder229.diary.DiaryApplication
import com.github.coder229.diary.security.AuthRequest
import com.github.coder229.diary.security.AuthResponse
import com.github.coder229.diary.security.TokenHandler
import com.github.coder229.diary.user.User
import com.github.coder229.diary.user.UserRepository
import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import java.time.LocalDateTime

import static com.jayway.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
/**
 * Created by scott on 9/17/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = [DiaryApplication.class])
@WebAppConfiguration
@IntegrationTest("server.port:0")
class AuthControllerTest {
    @Value('${local.server.port}')
    int serverPort

    @Autowired
    UserRepository userRepository

    @Autowired
    TokenHandler tokenHandler

    @Before
    void setup() {
        RestAssured.port = serverPort
    }

    @Test
    void 'User can login and receives an access token'() {
        AuthRequest request = new AuthRequest(username: 'user', password: 'changeme')

        // @formatter:on
        AuthResponse authResponse = given().
            log().all().
                contentType(ContentType.JSON).
                body(request).
            when().
                post("/login").
            then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("username", equalTo('user')).
                body("accessToken", notNullValue()).
            extract().as(AuthResponse)
        // @formatter:off

        User user = userRepository.findByUsername('user')

        assert user.accessToken == authResponse.accessToken
    }

    @Test
    void 'Incorrect password is rejected'() {
        AuthRequest request = new AuthRequest(username: 'user', password: 'changeme')

        // @formatter:on
        AuthResponse authResponse = given().
            log().all().
                contentType(ContentType.JSON).
                body(request).
            when().
                post("/login").
            then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("username", equalTo('user')).
                body("accessToken", notNullValue()).
            extract().as(AuthResponse)
        // @formatter:off

        User user = userRepository.findByUsername('user')
        assert user.accessToken == authResponse.accessToken
    }

    @Test
    void 'Valid access token is accepted'() {
        User user = userRepository.findByUsername('user')
        user.accessToken = tokenHandler.createTokenForUser(user, LocalDateTime.now().plusDays(1))
        userRepository.save(user)

        // @formatter:on
        given().
            log().all().
                header('X-AUTH-TOKEN', user.accessToken).
                contentType(ContentType.JSON).
            when().
                get("/userinfo").
            then().
                log().all().
                statusCode(HttpStatus.SC_OK).
                body("username", equalTo(user.username)).
                body("accessToken", equalTo(user.accessToken))
        // @formatter:off
    }

    @Test
    void 'Expired access token is rejected'() {
        User user = userRepository.findByUsername('user')
        user.accessToken = tokenHandler.createTokenForUser(user, LocalDateTime.now().minusDays(1))
        userRepository.save(user)

        // @formatter:on
        given().
            log().all().
                header('X-AUTH-TOKEN', user.accessToken).
                contentType(ContentType.JSON).
            when().
                get("/userinfo").
            then().
                log().all().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body("message", equalTo("Session expired"))
        // @formatter:off
    }
}
