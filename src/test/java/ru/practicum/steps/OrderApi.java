package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.OrderRequest;

import static io.restassured.RestAssured.given;
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


    @Step("Получить заказа по ID")
    public static Integer getOrderId(Response response) {
        return response.then().extract().body().jsonPath().get("order.id");
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


    @Step("Отправить запрос на принятие заказа - без курьера")
    public static Response sendPostRequestAcceptOrderWithoutCourier(Integer id) {

        return given()
                .header("Content-type", "application/json")
                .pathParam("id", id)
                .log().all()
                .when()
                .put(ACCEPT_ORDER_URL + "/{id}");
    }


}
