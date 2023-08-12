package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.OrderRequest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.Constants.*;

public class OrderApi {
    @Step("Отправить запрос на создание заказа")
    public static Response sendPostRequestCreateOrder(OrderRequest data) {

        return given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_ORDER_URL);
    }

    @Step("Проверить создание заказа")
    public static void checkCreateOrder(Response response) {
        response.then().assertThat().body("track", notNullValue()).statusCode(SC_CREATED);
    }

    @Step("Получить Track заказа")
    public static Integer getTrackOrder(Response response) {
        return response.then().extract().body().jsonPath().get("track");
    }

    @Step("Отправить запрос на получение заказа по Track")
    public static Response sendGetRequestOrder(Integer track) {

        return given()
                .header("Content-type", "application/json")
                .queryParam("t", track)
                .log().all()
                .when()
                .get(GET_ORDER_URL);
    }

    @Step("Отправить запрос на получение заказа без номера")
    public static Response sendGetRequestOrderWithoutNumber() {

        return given()
                .header("Content-type", "application/json")
                .queryParam("t", "")
                .log().all()
                .when()
                .get(GET_ORDER_URL);
    }

    @Step("Проверить получение заказа по Track")
    public static void checkGetOrder(Response response, Integer track) {
        response.then().assertThat().body("order.track", equalTo(track)).statusCode(SC_OK);
    }

    @Step("Получить заказа по ID")
    public static Integer getOrderId(Response response) {
        return response.then().extract().body().jsonPath().get("order.id");
    }

    @Step("Проверить получение заказа без номера")
    public static void checkGetOrderWithoutTrack(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверить получение заказа - несуществующий номер")
    public static void checkGetOrderUnrealTrack(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_NOT_FOUND);
    }

    @Step("Отправить запрос на принятие заказа")
    public static Response sendPostRequestAcceptOrder(Integer id, Integer courier) {

        return given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .queryParam("courierId", courier)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}");
    }

    @Step("Проверить принятие заказа")
    public static void checkAcceptOrder(Response response) {
        response.then().assertThat().body("ok", equalTo(true)).statusCode(SC_OK);
    }

    @Step("Отправить запрос на принятие заказа - без курьера")
    public static Response sendPostRequestAcceptOrderWithoutCourier(Integer id) {

        return given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}");
    }

    @Step("Проверить ошибку принятия заказа без курьера")
    public static void checkAcceptOrderWithoutCourier(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверить ошибку принятия заказа неправильный номер")
    public static void checkAcceptOrderUnreal(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_NOT_FOUND);
    }

}
