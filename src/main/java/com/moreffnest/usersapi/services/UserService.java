package com.moreffnest.usersapi.services;

import com.moreffnest.usersapi.exception.UserNotFoundException;
import com.moreffnest.usersapi.models.User;
import com.moreffnest.usersapi.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User saveUser(@Valid User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User addUser(User user) {
        return saveUser(user);
    }

    public User replaceUser(Long id, User user) {
        return userRepository.findById(id)
                .map(oldUser -> {
                    oldUser.setAddress(user.getAddress());
                    oldUser.setEmail(user.getEmail());
                    oldUser.setFirstName(user.getFirstName());
                    oldUser.setLastName(user.getLastName());
                    oldUser.setBirthDate(user.getBirthDate());
                    oldUser.setPhoneNumber(user.getPhoneNumber());
                    return saveUser(oldUser);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return saveUser(user);
                });
    }

    public User changeUser(Long id, User user) {
        return userRepository.findById(id)
                .map(oldUser -> {
                    if (user.getAddress() != null)
                        oldUser.setAddress(user.getAddress());
                    if (user.getEmail() != null)
                        oldUser.setEmail(user.getEmail());
                    if (user.getFirstName() != null)
                        oldUser.setFirstName(user.getFirstName());
                    if (user.getLastName() != null)
                        oldUser.setLastName(user.getLastName());
                    if (user.getBirthDate() != null)
                        oldUser.setBirthDate(user.getBirthDate());
                    if (user.getPhoneNumber() != null)
                        oldUser.setPhoneNumber(user.getPhoneNumber());
                    return saveUser(oldUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> findAllUsersByBirthDateRange(Pageable pageable, LocalDate from, LocalDate to) {
        return userRepository.findAllByBirthDateBetween(pageable, from, to);
    }
}
