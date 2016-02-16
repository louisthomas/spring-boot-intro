package com.example.controller;


import com.example.DemoApplication;
import com.example.domain.Todo;
import com.example.repository.TodoRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@IntegrationTest
public class TodoControllerTest {


    private Todo firstTodo;
    private Todo secondTodo;


    @Autowired
    private TodoRepository repository;

    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() {
        repository.deleteAll();
        firstTodo = repository.save(new Todo("test 1", "test 1 description"));
        secondTodo = repository.save(new Todo("test 2", "test 2 description"));
        RestAssured.port = serverPort;
    }


    //http://www.jayway.com/2013/12/10/json-schema-validation-with-rest-assured/
    @Test
    public void getItemsShouldReturnBothItems() {

        Response response = get("/api/todo");
        System.out.println("RESPONSE: " + response.asString());


        Todo[] todoList = given().when().get("/api/todo").as(Todo[].class);
        Assert.assertEquals(2, todoList.length);

        when()
                .get("/api/todo")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", hasItems("test 1","test 2"));
      }

}
