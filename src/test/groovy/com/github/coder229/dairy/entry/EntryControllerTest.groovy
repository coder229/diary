package com.github.coder229.dairy.entry

import com.github.coder229.diary.DiaryApplication
import com.github.coder229.diary.entry.Entry
import com.github.coder229.diary.security.TokenHandler
import com.github.coder229.diary.user.User
import com.github.coder229.diary.user.UserRepository
import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.path.json.JsonPath
import com.jayway.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.Assert
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
 * Created by scott on 9/2/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = [DiaryApplication.class])
@WebAppConfiguration
@IntegrationTest("server.port:0")
class EntryControllerTest {
    static final String BaseURL = "/api/user"

    @Value('${local.server.port}')
    int serverPort

    @Autowired
    UserRepository userRepository

    @Autowired
    TokenHandler tokenHandler

    User user

    @Before
    void setup() {
        RestAssured.port = serverPort
        user = userRepository.findByUsername('user')
        user.accessToken = tokenHandler.createTokenForUser(user, LocalDateTime.now().plusDays(1))
        userRepository.save(user)
    }

    @Test
    void testCreateGetRemoveEntry() {
        Entry entry = new Entry(
                userId: "abc" + System.currentTimeMillis(),
                date: new Date(),
                notes: "Notes")

        // @formatter:off
        Response response = given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
                body(entry).
            when().
                post("/api/entries").
            then().
                statusCode(HttpStatus.SC_CREATED).
                body("id", notNullValue()).
                body("notes", equalTo(entry.getNotes())).
                body("date", notNullValue()).
            extract().
                response()
        // @formatter:on

        String json = response.getBody().prettyPrint()
        String entryId = JsonPath.from(json).getString("id")

        // @formatter:off
        response = given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
            when().
                get("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK).
                body("id", equalTo(entryId)).
                body("notes", equalTo(entry.getNotes())).
                body("date", equalTo(entry.getDate().getTime())).
            extract().
                response()
        // @formatter:on

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
            when().
                delete("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK)
        // @formatter:on

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
            when().
                get("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_NOT_FOUND)
        // @formatter:on
    }

    @Test
    public void testCreateListRemoveEntry() {
        Entry entry = new Entry(
                userId: "abc" + System.currentTimeMillis(),
                date: new Date(),
                notes: "Notes")

        // @formatter:off
        Response response = given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
                body(entry).
            when().
                post("/api/entries").
            then().
                statusCode(HttpStatus.SC_CREATED).
                body("id", notNullValue()).
                body("notes", equalTo(entry.getNotes())).
                body("date", notNullValue()).
            extract().
                response()
        // @formatter:on

        String json = response.getBody().prettyPrint()
        String entryId = JsonPath.from(json).getString("id")

        // @formatter:off
        response = given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
            when().
                get("/api/entries").
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                response()
        // @formatter:on

        json = response.getBody().prettyPrint()
        List<Map<String,?>> entries = JsonPath.from(json).getList(".")
        boolean found = false

        entries.each { map->
            if (map.get("id").equals(entryId)) {
                Assert.assertEquals(entry.getDate().getTime(), map.get("date"))
                Assert.assertEquals(entry.getNotes(), map.get("notes"))
                Assert.assertEquals(entry.getUserId(), map.get("userId"))
                found = true
            }
        }
        Assert.assertTrue(found)

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                header('X-AUTH-TOKEN', user.accessToken).
            when().
                delete("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK)
        // @formatter:on
    }
}