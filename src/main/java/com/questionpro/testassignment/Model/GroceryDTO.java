package com.questionpro.testassignment.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class GroceryDTO {
    private String groceryId;

    private String name;

    private double price;

    private String category;
}
