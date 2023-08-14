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

public class GetOrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру")
    public void getOrderTest() {
        OrderRequest data = getOrderBody();
        Response response = sendPostRequestCreateOrder(data);
        checkCreateOrder(response);
        Integer track = getTrackOrder(response);
        Response responseOrder = sendGetRequestOrder(track);
        checkGetOrder(responseOrder, track);
    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру - без номера")
    public void getOrderWithoutNumberTest() {
        Response responseOrder = sendGetRequestOrderWithoutNumber();
        checkGetOrderWithoutTrack(responseOrder, "Недостаточно данных для поиска");
    }

    @Test
    @Feature("Order")
    @Story("Order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получить заказ по его номеру - несуществующий номер")
    public void getOrderUnrealNumberTest() {
        Response responseOrder = sendGetRequestOrder(555554);
        checkGetOrderUnrealTrack(responseOrder, "Заказ не найден");
    }
}
