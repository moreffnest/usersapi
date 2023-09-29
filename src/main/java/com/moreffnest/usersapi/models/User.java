package com.moreffnest.usersapi.models;


import com.moreffnest.usersapi.util.MinimumAge;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "usr")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@SequenceGenerator(name = "usr_seq", sequenceName = "usr_seq", initialValue = 5)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_seq")
    private long id;
    @NotBlank(message = "user must have first name!")
    private String firstName;
    @NotBlank(message = "user must have last name!")
    private String lastName;
    @MinimumAge
    private LocalDate birthDate;
    @NotBlank(message = "user must have email address!")
    @Email(message = "email is not valid!")
    private String email;
    @Embedded
    @Valid
    private Address address;
    private String phoneNumber;

}
