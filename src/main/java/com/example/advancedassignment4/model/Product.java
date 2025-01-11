package com.example.advancedassignment4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Use lombok to automatically make the setters, getters and constructors
@Entity
@Table(name="ProductTable")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    // Standard Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Can't be null, if it is, an error will be thrown.
    @NotNull(message = "Name must not be null!")
    private String name;

    // These work slightly different. Numbers default to zero if they would otherwise be null, which is perfectly fine. They also can't be negative, if they are, an error will be thrown
    @NotNull(message = "Price must not be null!")
    @Min(value = 0, message = "Price must not be negative!")
    private double price;
    @NotNull(message = "Quantity must not be null!")
    @Min(value = 0, message = "Quantity must not be negative!")
    private int quantity;
}
