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
import ru.practicum.models.OrderRequest;

import static ru.practicum.Constants.BASE_URL;
import static ru.practicum.checks.OrderCheck.*;
import static ru.practicum.data.DataGenerator.getOrderBody;
import static ru.practicum.steps.OrderApi.*;

public class OrdersAcceptTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ")
    public void acceptOrdersTest() {
        OrderRequest data = getOrderBody();
        // Создаем заказ
        Response response = sendPostRequestCreateOrder(data);
        checkCreateOrder(response);
        Integer track = getTrackOrder(response);
        Response responseOrder = sendGetRequestOrder(track);
        checkGetOrder(responseOrder, track);
        // Получаем ID
        Integer id = getOrderId(responseOrder);
        // Принимаем заказ
        Response accept = sendPostRequestAcceptOrder(id, 213004);
        checkAcceptOrder(accept);

    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - без курьера")
    public void acceptOrdersWithoutCourierTest() {
        OrderRequest data = getOrderBody();
        // Создаем заказ
        Response response = sendPostRequestCreateOrder(data);
        checkCreateOrder(response);
        Integer track = getTrackOrder(response);
        Response responseOrder = sendGetRequestOrder(track);
        checkGetOrder(responseOrder, track);
        // Получаем ID
        Integer id = getOrderId(responseOrder);
        // Принимаем заказ
        Response accept = sendPostRequestAcceptOrderWithoutCourier(id);
        checkAcceptOrderWithoutCourier(accept, "Недостаточно данных для поиска");

    }


    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - неправильный номер заказа")
    public void acceptOrdersInvalidNumberTest() {
        Response accept = sendPostRequestAcceptOrder(56465442, 213004);
        checkAcceptOrderUnreal(accept, "Заказа с таким id не существует");
    }

    @Test
    @Feature("Accept")
    @Story("Accept")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Принять заказ - неправильный ID курьера")
    public void acceptOrdersInvalidIdTest() {
        Response accept = sendPostRequestAcceptOrder(56465442, 213000);
        checkAcceptOrderUnreal(accept, "Курьера с таким id не существует");
    }

}
