package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTests {

    private final CourierSteps courierSteps = new CourierSteps();
    private Courier courier;


    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Успешный логин возвращает id")
    public void shouldReturnId(){
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.createCourier(courier);
        courierSteps.login(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Получаем ошибку если логиниться без логина")
    public void shouldReturnError400ForLogin() {
        courier = new Courier();
        courier.setPassword("1233221");
        courierSteps.login(courier)
                .statusCode(400)
                .body("message",is("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Ошибка при логине с неизвестными данными")
    public void shouldReturnError404ForUnknownData() {
        courier = new Courier();
        courier.setLogin("random_login");
        courier.setPassword("random_password");
        courierSteps.login(courier)
                .statusCode(404)
                .body("message",is("Учетная запись не найдена"));
    }
}



