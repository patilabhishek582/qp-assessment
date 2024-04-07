package com.questionpro.testassignment.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String OrderID;

    private String UserID;

    private List<String> groceryName;

    private List<Integer> quantityPerItem;

}
