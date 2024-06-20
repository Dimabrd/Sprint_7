package ru.praktikum.steps;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.Courier;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.praktikum.config.RestConfig.HOST;

public class CourierSteps {

    private static final String CREATE = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/{id}";
    @Step
    public ValidatableResponse createCourier(Courier courier){
        return  given().log().ifValidationFails()
                .contentType(JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(CREATE)
                .then();
    }
    @Step
    public ValidatableResponse login (Courier courier){
        return   given()
                .contentType(JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }
    @Step
    public ValidatableResponse delete(Courier courier){
        return   given()
                .contentType(JSON)
                .baseUri(HOST)
                .pathParam("id", courier.getId())
                .when()
                .delete(DELETE_COURIER)
                .then();
    }

}


