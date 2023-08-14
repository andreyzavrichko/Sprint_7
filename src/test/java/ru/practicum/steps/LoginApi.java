package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.LoginCourierRequest;

import static io.restassured.RestAssured.given;
import static ru.practicum.Constants.LOGIN_COURIER_URL;

public class LoginApi {
    @Step("Отправить запрос на авторизацию с валидными значениями")
    public static Response sendPostRequestLogin(LoginCourierRequest data) {
        return given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL);
    }




}
