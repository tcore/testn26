package com.tcore.controller;

import com.jayway.restassured.RestAssured;
import com.tcore.Testn26Application;
import com.tcore.dto.Transaction;
import com.tcore.service.TransactionService;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Testn26Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TransactionControllerIntegrationTest {
    @Value("${local.server.port}")
    int port;

    @Autowired
    TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService.create(1L, new Transaction(1.5, "type1"));
        transactionService.create(2L, new Transaction(2D, "type2"));
        transactionService.create(3L, new Transaction(3D, "type2"));
        transactionService.create(4L, new Transaction(4D, "type3", 3L));
        transactionService.create(5L, new Transaction(5D, "type3", 3L));
        transactionService.create(6L, new Transaction(5D, "type2", 5L));
        transactionService.create(7L, new Transaction(5D, "type2", 5L));
        transactionService.create(8L, new Transaction(5D, "type2", 7L));
        RestAssured.port = port;
    }

    @Test
    public void testTransactionNotFound() {
        when()
                .get("transactionservice/transaction/{transactionId}", 9999)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    public void testGet() {
        get(1, nullValue(), is(1.5f), equalTo("type1"));
    }

    @Test
    public void testTypes() {
        when()
                .get("/transactionservice/types/{type}", "type3")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(is("[4,5]"))
        ;
    }

    @Test
    public void testSum() {
        when()
                .get("/transactionservice/sum/{transactionId}", 3)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("sum", is(24f))
        ;
    }

    private void get(int transactionId, Matcher parentId, Matcher amount, Matcher type) {
        when()
                .get("transactionservice/transaction/{transactionId}", transactionId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("parentId", parentId)
                .body("amount", amount)
                .body("type", type)
        ;
    }
}
