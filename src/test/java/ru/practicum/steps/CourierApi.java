package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.CreateCourierRequest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.Constants.CREATE_COURIER_URL;
import static ru.practicum.Constants.LOGIN_COURIER_URL;

public class CourierApi {

    @Step("Отправить запрос на создание курьера")
    public static Response sendPostRequestCreateCourier(CreateCourierRequest data) {

        return given()
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

    @Step("Проверить создание курьера")
    public static void checkCreateCourier(Response response) {
        response.then().assertThat().body("ok", equalTo(true)).statusCode(SC_CREATED);
    }

    @Step("Проверить ошибку создания курьера без логина")
    public static void checkCreateCourierWithoutLogin(Response response) {
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверить ошибку создание курьера - логин уже используется")
    public static void checkCreateDoubleCourier(Response response) {
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).statusCode(SC_CONFLICT);
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


    @Step("Проверить удаление курьера")
    public static void checkDeleteCourier(Response response) {
        response.then().assertThat().body("ok", equalTo(true)).statusCode(SC_OK);
    }

    @Step("Проверить удаление несуществующего курьера")
    public static void checkDeleteUnrealCourier(Response response) {
        response.then().assertThat().body("message", equalTo("Курьера с таким id нет.")).statusCode(SC_NOT_FOUND);
    }


}
