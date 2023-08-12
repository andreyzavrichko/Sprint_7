package ru.practicum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.models.LoginCourierRequest;

import static ru.practicum.Constants.BASE_URL;
import static ru.practicum.data.DataGenerator.*;
import static ru.practicum.steps.LoginApi.*;

public class LoginCourierTest {
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
        Response response = sendPostRequestLogin(data);
        checkValidLogin(response,213004);
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация- невалидный логин")
    public void invalidLoginTest() {
        LoginCourierRequest data = getInValidLogin();
        Response response = sendPostRequestLogin(data);
        checkInValidLogin(response, "Учетная запись не найдена");
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация - невалидный пароль")
    public void invalidPassTest() {
        LoginCourierRequest data = getInValidPassLogin();
        Response response = sendPostRequestLogin(data);
        checkInValidLogin(response, "Учетная запись не найдена");
    }

    @Test
    @Feature("Login")
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Авторизация - без пароля")
    public void withoutLoginTest() {
        LoginCourierRequest data = getWithoutPassLogin();
        Response response = sendPostRequestLogin(data);
        checkLoginWithoutPass(response, "Недостаточно данных для входа");
    }

}
