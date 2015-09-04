package com.github.coder229.dairy.controller;

import com.github.coder229.dairy.DiaryApplication;
import com.github.coder229.dairy.model.Entry;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

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


    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testCreateEntry() {
        Entry entry = new Entry();
        entry.setDate(LocalDate.now());
        entry.setNotes("Notes");

        // @formatter:off
        given().
            body(entry).
        when().
            post("/api/entries").
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("id", notNullValue()).
            body("notes", equalTo(entry.getNotes())).
            body("date", notNullValue());
        // @formatter:on
    }

}