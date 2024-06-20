package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderSteps orderSteps = new OrderSteps();
    private final String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }
    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Parameterized.Parameters
    public static Collection<Object[]> provideOrderColours(){
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        });
    }



    @Test
    @DisplayName("Проверка создания заказа")
    public void shouldReturnTrack(){
        Order order = new Order();
        order.setFirstName("Naruto");
        order.setLastName("Uchiha");
        order.setAddress("Konoha, 142 apt.");
        order.setMetroStation(4);
        order.setPhone("+7 800 355 35 35");
        order.setRentTime(5);
        order.setDeliveryDate("2020-06-06");
        order.setComment("Saske, come back to Konoha");
        order.setColors(colors);

        orderSteps.createOrder(order)
                .statusCode(201).body("track", notNullValue());
    }
}

