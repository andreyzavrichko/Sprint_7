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
import ru.practicum.models.OrderRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.data.DataGenerator.getOrderBody;

public class GetOrderTest extends TestBase {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру")
    public void getOrderTest() {
        // Создаем заказ
        OrderRequest data = getOrderBody();
        Integer response = given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("track", notNullValue())
                .statusCode(201)
                .extract().body().jsonPath().get("track");

        // Получаем заказ по его номеру
        given()
                .header("Content-type", "application/json")
                .queryParam("t", response)
                .log().all()
                .when()
                .get(GET_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("order.track", equalTo(response))
                .statusCode(200);

    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру - без номера")
    public void getOrderWithoutNumberTest() {

        // Получаем заказ по его номеру
        given()
                .header("Content-type", "application/json")
                .queryParam("t", "")
                .log().all()
                .when()
                .get(GET_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .statusCode(400);

    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру - несуществующий номер")
    public void getOrderUnrealNumberTest() {

        // Получаем заказ по его номеру
        given()
                .header("Content-type", "application/json")
                .queryParam("t", "888888888")
                .log().all()
                .when()
                .get(GET_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Заказ не найден"))
                .statusCode(404);

    }
}
