package com.moreffnest.usersapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalDateRange {
    private LocalDate from;
    private LocalDate to;

    public boolean isValid() {
        return from.isBefore(to);
    }
}
