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
import ru.practicum.models.LoginCourierRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.data.DataGenerator.*;

public class LoginCourierTest extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация с валидными значениями")
    public void validLoginTest() {
        LoginCourierRequest data = getValidLogin();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("id", equalTo(213004))
                .statusCode(200);
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация- невалидный логин")
    public void invalidLoginTest() {
        LoginCourierRequest data = getInValidLogin();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация - невалидный пароль")
    public void invalidPassTest() {
        LoginCourierRequest data = getInValidPassLogin();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация - без пароля")
    public void withoutLoginTest() {
        LoginCourierRequest data = getWithoutPassLogin();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

}
