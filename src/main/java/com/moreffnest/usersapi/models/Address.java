package com.moreffnest.usersapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Address {
    @NotBlank(message = "address must have a country!")
    private String country;
    @NotBlank(message = "address must have a city!")
    private String city;
    @NotBlank(message = "address must have a street!")
    private String street;
    @Positive(message = "house number must be positive!")
    private Integer houseNumber;
}

