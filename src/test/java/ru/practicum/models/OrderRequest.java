package ru.practicum.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
    public String firstName;
    public String lastName;
    public String address;
    public Integer metroStation;
    public String phone;
    public Integer rentTime;
    public String deliveryDate;
    public String comment;
    public ArrayList<String> color;
}
