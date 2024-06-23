package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CourierSteps;
import static org.hamcrest.CoreMatchers.is;

public class CourierCreateTests {

    private final CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("200 - ok при создании курьера")
    public void shouldReturnOkTrue(){
        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }
    @Test
    @DisplayName("Нельзя создать дубликат курьера")
    public void shouldNotAllowCreatingDuplicateCourier() {
        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
        courierSteps.createCourier(courier)
                .statusCode(409) // предполагаемый код ошибки для дубликатов
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Ошибка при создании без логина")
    public void shouldReturnErrorForMissingLogin() {
        // Удаление логина
        courier.setLogin(null);
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Integer id = courierSteps.login(courier)
                .extract()
                .body()
                .path("id");

        if (id != null) {
            courier.setId(id);
            courierSteps.delete(courier)
                    .statusCode(200);
        }
    }
}




