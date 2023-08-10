package ru.practicum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.TestBase;
import ru.practicum.models.OrdersResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest extends TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Orders")
    @Story("Orders")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение списка заказов")
    public void getOrdersTest() {

        List<OrdersResponse> response = given()
                .header("Content-type", "application/json")
                .queryParam("limit", 10)
                .log().all()
                .when()
                .get(LIST_ORDER_URL)
                .then()
                .log().all()
                .statusCode(200)
                .assertThat().body("orders.track", notNullValue())
                .extract().body().jsonPath().getList("orders", OrdersResponse.class);


    }
}
