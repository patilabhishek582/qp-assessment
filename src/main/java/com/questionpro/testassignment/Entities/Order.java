package com.questionpro.testassignment.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_Table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String OrderID;

    private String UserID;

    private List<String> groceryName;

    private List<Double> quantityPerItem;

}
