package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.CreateCourierRequest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static ru.practicum.Constants.CREATE_COURIER_URL;
import static ru.practicum.Constants.LOGIN_COURIER_URL;
import static ru.practicum.CustomAllureListener.withCustomTemplates;

public class CourierApi {

    @Step("Отправить запрос на создание курьера")
    public static Response sendPostRequestCreateCourier(CreateCourierRequest data) {

        return given()
                .filter(withCustomTemplates())
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL);
    }

    @Step("Отправить запрос на создание курьера без логина")
    public static Response sendPostRequestCreateCourierWithoutLogin(CreateCourierRequest data) {

        return given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL);
    }




    @Step("Получить ID курьера")
    public static Integer getIdCourier(CreateCourierRequest data) {
        return given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .extract().body().jsonPath().get("id");
    }


    @Step("Удалить курьера")
    public static Response deleteCourier(Integer id) {
        return given()
                .pathParams("id", id)
                .log().all()
                .when()
                .delete(CREATE_COURIER_URL + "/{id}");
    }




}
