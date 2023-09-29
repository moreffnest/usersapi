package com.moreffnest.usersapi.unit;

import com.moreffnest.usersapi.exception.UserNotFoundException;
import com.moreffnest.usersapi.models.User;
import com.moreffnest.usersapi.repositories.UserRepository;
import com.moreffnest.usersapi.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void userServiceInit() {
        userService = new UserService(userRepository);
    }

    @Test
    void changeUserSuccess() {
        User user = User.builder()
                .firstName("Alex")
                .lastName("Jones")
                .birthDate(LocalDate.of(2000, 1, 1))
                .email("simplemail@gmail.com")
                .build();

        User changeInfo = User.builder()
                .firstName("Johny")
                .lastName("Jones")
                .email("complicatedmail@gmail.com")
                .build();

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        userService.changeUser(1L, changeInfo);
        verify(userRepository).save(user);
        assertEquals(changeInfo.getFirstName(), user.getFirstName());
        assertEquals(changeInfo.getEmail(), user.getEmail());
        assertEquals(changeInfo.getLastName(), user.getLastName());
    }

    @Test
    void changeUserFailed() {
        User changeInfo = User.builder()
                .firstName("Johny")
                .lastName("Jones")
                .email("complicatedmail@gmail.com")
                .build();

        when(userRepository.findById(any()))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () ->
                userService.changeUser(1L, changeInfo));
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).save(any(User.class));
    }



}
