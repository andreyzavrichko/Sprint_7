package ru.practicum.data;

import com.github.javafaker.Faker;
import ru.practicum.models.CreateCourierRequest;
import ru.practicum.models.LoginCourierRequest;
import ru.practicum.models.OrderRequest;

import java.util.ArrayList;

public class DataGenerator {
    public static CreateCourierRequest getCourier() {
        Faker faker = new Faker();
        return CreateCourierRequest.builder()
                .login(faker.name().username())
                .password(faker.internet().password())
                .firstName(faker.name().firstName())
                .build();
    }

    public static CreateCourierRequest getCourierWithoutLogin() {
        Faker faker = new Faker();
        return CreateCourierRequest.builder()

                .password(faker.internet().password())
                .firstName(faker.name().firstName())
                .build();
    }

    public static LoginCourierRequest getValidLogin() {
        return LoginCourierRequest.builder()
                .login("andrey1")
                .password("12345")
                .build();
    }

    public static LoginCourierRequest getInValidLogin() {
        return LoginCourierRequest.builder()
                .login("dd")
                .password("dd")
                .build();
    }

    public static LoginCourierRequest getInValidPassLogin() {
        return LoginCourierRequest.builder()
                .login("andrey1")
                .password("dd")
                .build();
    }

    public static LoginCourierRequest getWithoutPassLogin() {
        return LoginCourierRequest.builder()
                .login("andrey1")
                .password("")
                .build();
    }

    public static OrderRequest getOrderBody() {
        Faker faker = new Faker();
        ArrayList<String> data = new ArrayList<>();
        data.add("BLACK");
        return OrderRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .address(faker.address().streetAddress())
                .metroStation(4)
                .phone(String.valueOf(faker.phoneNumber()))
                .rentTime(5)
                .deliveryDate("2023-10-08")
                .comment(faker.name().nameWithMiddle())
                .color(data)
                .build();
    }


}
