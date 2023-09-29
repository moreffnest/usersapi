package com.moreffnest.usersapi.repositories;

import com.moreffnest.usersapi.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByBirthDateBetween(Pageable pageable, LocalDate birthDateStart,
                                         LocalDate birthDateEnd);
}
