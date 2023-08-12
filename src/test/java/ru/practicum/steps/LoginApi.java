package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.models.LoginCourierRequest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
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
