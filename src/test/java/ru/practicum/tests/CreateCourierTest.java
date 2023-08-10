package ru.practicum.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.TestBase;
import ru.practicum.models.CreateCourierErrorResponse;
import ru.practicum.models.CreateCourierRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.data.DataGenerator.getCourier;
import static ru.practicum.data.DataGenerator.getCourierWithoutLogin;

public class CreateCourierTest extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Courier")
    @Story("Courier")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание курьера")
    public void createCourierTest() {
        CreateCourierRequest data = getCourier();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("ok", equalTo(true))
                .statusCode(201);
    }

    @Test
    @Feature("Courier")
    @Story("Courier")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание курьера - без логина")
    public void createCourierWithoutLoginTest() {
        CreateCourierRequest data = getCourierWithoutLogin();

        CreateCourierErrorResponse response = given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL)
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(CreateCourierErrorResponse.class);
        Assert.assertEquals("Недостаточно данных для создания учетной записи", response.getMessage());
    }

    @Test
    @Feature("Courier")
    @Story("Courier")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание курьера - уже существует")
    public void createCourierDoubleTest() {
        CreateCourierRequest data = getCourier();

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("ok", equalTo(true))
                .statusCode(201);

        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @Test
    @Feature("Courier")
    @Story("Courier")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Удаление курьера")
    public void deleteCourierTest() {
        CreateCourierRequest data = getCourier();
        // Создали курьера
        given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(CREATE_COURIER_URL)
                .then()
                .log().all()
                .assertThat().body("ok", equalTo(true))
                .statusCode(201);
        // Получили id
        Integer response = given()
                .header("Content-type", "application/json")
                .body(data)
                .log().all()
                .when()
                .post(LOGIN_COURIER_URL)
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().get("id");

        // Удаляем курьера
        given()
                .pathParams("id", response)
                .log().all()
                .when()
                .delete(CREATE_COURIER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("ok", equalTo(true))
                .statusCode(200);


    }

    @Test
    @Feature("Courier")
    @Story("Courier")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Удаление курьера - без ID")
    public void deleteCourierWithoutIdTest() {
        // Удаляем курьера
        given()
                .pathParams("id", "544545")
                .log().all()
                .when()
                .delete(CREATE_COURIER_URL + "/{id}")
                .then()
                .log().all()
                .assertThat().body("message", equalTo("Курьера с таким id нет."))
                .statusCode(404);
    }


}
