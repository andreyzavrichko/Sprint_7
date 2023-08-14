package ru.practicum.checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCheck {
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

    @Step("Проверить удаление курьера")
    public static void checkDeleteCourier(Response response) {
        response.then().assertThat().body("ok", equalTo(true)).statusCode(SC_OK);
    }

    @Step("Проверить удаление несуществующего курьера")
    public static void checkDeleteUnrealCourier(Response response) {
        response.then().assertThat().body("message", equalTo("Курьера с таким id нет.")).statusCode(SC_NOT_FOUND);
    }

}
