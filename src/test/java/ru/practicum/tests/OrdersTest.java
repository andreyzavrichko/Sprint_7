package ru.practicum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.practicum.Constants.BASE_URL;
import static ru.practicum.Constants.CREATE_ORDER_URL;

@RunWith(Parameterized.class)
public class OrdersTest {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final Integer metroStation;
    public final String phone;
    public final Integer rentTime;
    public final String deliveryDate;
    public final String comment;
    public final List color;

    public OrdersTest(String firstName, String lastName, String address, Integer metroStation, String phone, Integer rentTime, String deliveryDate, String comment, List color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Parameterized.Parameters
    public static Object[][] getOrder() {
        return new Object[][]{
                {"Аркадий", "Иванов", "dd, 14.", 4, "+7 800 355 35 35", 5, "2023-06-06", "Comment", List.of("BLACK")},
                {"Эдуард", "Петров", "sds, 142apt.", 5, "+7 800 355 35 35", 5, "2023-06-06", "Comment", List.of("BLACK", "GREY")},
                {"Александр", "Пушкин", "sds, 142apt.", 5, "+7 800 355 35 35", 5, "2023-06-06", "Comment", List.of("GREY")},
                {"Антон", "Колотушкин", "aa, 142apt.", 6, "+7 800 355 35 35", 5, "2026-06-06", "Comment", List.of("")},

        };
    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание заказа")
    public void createOrdersTest() {
        given()
                .header("Content-type", "application/json")
                .body(getOrder())
                .log().all()
                .when()
                .post(CREATE_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("track", notNullValue())
                .statusCode(SC_CREATED);
    }


}
