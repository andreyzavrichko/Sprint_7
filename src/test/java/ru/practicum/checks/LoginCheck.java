package ru.practicum.checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginCheck {
    @Step("Проверить успешную авторизацию")
    public static void checkValidLogin(Response response, Integer id) {
        response.then().assertThat().body("id", equalTo(id)).statusCode(SC_OK);
    }

    @Step("Проверить авторизацию при невалидном логине")
    public static void checkInValidLogin(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_NOT_FOUND);
    }

    @Step("Проверить авторизацию без пароля")
    public static void checkLoginWithoutPass(Response response, String data) {
        response.then().assertThat().body("message", equalTo(data)).statusCode(SC_BAD_REQUEST);
    }
}
