package com.questionpro.testassignment.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grocery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  groceryId;

    private String name;

    private double price;

    private String category;
}
