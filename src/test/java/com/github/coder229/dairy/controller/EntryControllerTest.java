package com.github.coder229.dairy.controller;

import com.github.coder229.dairy.DiaryApplication;
import com.github.coder229.dairy.model.Entry;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by scott on 9/2/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DiaryApplication.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class EntryControllerTest {
    private static final String BaseURL = "/api/user";

    @Value("${local.server.port}")
    int serverPort;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testCreateGetRemoveEntry() {
        Entry entry = new Entry();
        entry.setUserId("abc" + System.currentTimeMillis());
        entry.setDate(new Date());
        entry.setNotes("Notes");

        // @formatter:off
        Response response = given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
                body(entry).
            when().
                post("/api/entries").
            then().
                statusCode(HttpStatus.SC_CREATED).
                body("id", notNullValue()).
                body("notes", equalTo(entry.getNotes())).
                body("date", notNullValue()).
            extract().
                response();
        // @formatter:on

        String json = response.getBody().prettyPrint();
        String entryId = JsonPath.from(json).getString("id");

        // @formatter:off
        response = given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
            when().
                get("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK).
                body("id", equalTo(entryId)).
                body("notes", equalTo(entry.getNotes())).
                body("date", equalTo(entry.getDate().getTime())).
            extract().
                response();
        // @formatter:on

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
            when().
                delete("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK);
        // @formatter:on

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
            when().
                get("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_NOT_FOUND);
        // @formatter:on
    }


    @Test
    public void testCreateListRemoveEntry() {
        Entry entry = new Entry();
        entry.setUserId("abc" + System.currentTimeMillis());
        entry.setDate(new Date());
        entry.setNotes("Notes");

        // @formatter:off
        Response response = given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
                body(entry).
            when().
                post("/api/entries").
            then().
                statusCode(HttpStatus.SC_CREATED).
                body("id", notNullValue()).
                body("notes", equalTo(entry.getNotes())).
                body("date", notNullValue()).
            extract().
                response();
        // @formatter:on

        String json = response.getBody().prettyPrint();
        String entryId = JsonPath.from(json).getString("id");

        // @formatter:off
        response = given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
            when().
                get("/api/entries").
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                response();
        // @formatter:on

        json = response.getBody().prettyPrint();
        List<Map<String,?>> entries = JsonPath.from(json).getList(".");
        boolean found = false;

        for (Map<String,?> map : entries) {
            if (map.get("id").equals(entryId)) {
                Assert.assertEquals(entry.getDate().getTime(), map.get("date"));
                Assert.assertEquals(entry.getNotes(), map.get("notes"));
                Assert.assertEquals(entry.getUserId(), map.get("userId"));
                found = true;
                break;
            }
        }
        Assert.assertTrue("Entry not found in list", found);

        // @formatter:off
        given().
                contentType(ContentType.JSON).
                auth().basic("user", "password").
            when().
                delete("/api/entries/" + entryId).
            then().
                statusCode(HttpStatus.SC_OK);
        // @formatter:on
    }
}