package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.Order;

import static io.restassured.RestAssured.given;
import static ru.praktikum.config.RestConfig.HOST;

public class OrderSteps {
    @Step
    public ValidatableResponse createOrder(Order order){
        return   given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }
    @Step
    public ValidatableResponse getOrders(){
        return given()
                .baseUri(HOST)
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
