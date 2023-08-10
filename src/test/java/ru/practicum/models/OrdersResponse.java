package ru.practicum.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersResponse {
    public String id;
    public String courierId;
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public String rentTime;
    public String deliveryDate;
    public String track;
    public String comment;
    public String createdAt;
    public String updatedAt;
    public Integer status;
}
