package com.questionpro.testassignment.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"OrderID","UserID","totalPrice"})
public class OrderResponse {

    @JsonProperty("OrderID")
    private String orderID;

    @JsonProperty("UserID")
    private String UserID;

    @JsonProperty("totalPrice")
    private double totalPrice;
}
