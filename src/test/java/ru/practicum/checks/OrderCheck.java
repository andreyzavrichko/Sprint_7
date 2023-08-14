package ru.practicum.checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrderCheck {
    @Step("Проверить создание заказа")
    public static void checkCreateOrder(Response response) {
        response.then().assertThat().body("track", notNullValue()).statusCode(SC_CREATED);
    }

    @Step("Проверить получение заказа по Track")
    public static void checkGetOrder(Response response, Integer track) {
        response.then().assertThat().body("order.track", equalTo(track)).statusCode(SC_OK);
    }

    @Step("Проверить получение заказа без номера")
    public static void checkGetOrderWithoutTrack(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_BAD_REQUEST);
    }
    @Step("Проверить получение заказа - несуществующий номер")
    public static void checkGetOrderUnrealTrack(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_NOT_FOUND);
    }
    @Step("Проверить ошибку принятия заказа без курьера")
    public static void checkAcceptOrderWithoutCourier(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверить ошибку принятия заказа неправильный номер")
    public static void checkAcceptOrderUnreal(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_NOT_FOUND);
    }
    @Step("Проверить принятие заказа")
    public static void checkAcceptOrder(Response response) {
        response.then().assertThat().body("ok", equalTo(true)).statusCode(SC_OK);
    }
}
