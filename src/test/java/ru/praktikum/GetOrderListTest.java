package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    private final OrderSteps ordersteps = new OrderSteps();

    @Before
    public void setUp(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void shouldReturnListOfOrders(){
        ordersteps.getOrders()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}


