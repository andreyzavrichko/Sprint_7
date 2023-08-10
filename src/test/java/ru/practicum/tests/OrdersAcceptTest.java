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

public class OrdersAcceptTest extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ")
    public void acceptOrdersTest() {
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
        Integer id = given()
                .header("Content-type", "application/json")
                .queryParam("t", response)
                .log().all()
                .when()
                .get(GET_ORDER_URL)
                .then()
                .log().all()
                .assertThat().body("order.track", equalTo(response))
                .statusCode(200)
                .extract().body().jsonPath().get("order.id");


        // Принимаем заказ
        given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .queryParam("courierId", 213004)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("ok", equalTo(true))
                .statusCode(200);

    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - без курьера")
    public void acceptOrdersWithoutCourierTest() {
        given()
                .pathParam("id", "2")
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .statusCode(400);

    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - неправильный номер заказа")
    public void acceptOrdersInvalidNumberTest() {

        given()
                .pathParam("id", "56465442")
                .queryParam("courierId", 213004)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Заказа с таким id не существует"))
                .statusCode(404);

    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - неправильный ID курьера")
    public void acceptOrdersInvalidIdTest() {

        given()
                .pathParam("id", "56465442")
                .queryParam("courierId", 213000)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Курьера с таким id не существует"))
                .statusCode(404);

    }

}
