package com.moreffnest.usersapi.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moreffnest.usersapi.controllers.UserController;
import com.moreffnest.usersapi.exception.UserExceptionHandler;
import com.moreffnest.usersapi.hateoas.UserModelAssembler;
import com.moreffnest.usersapi.models.User;
import com.moreffnest.usersapi.repositories.UserRepository;
import com.moreffnest.usersapi.services.UserService;
import com.moreffnest.usersapi.unit.config.UserControllerTestConfig;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserControllerTestConfig.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void addUserSuccess(@Autowired UserService userService
    ) throws Exception {
        User user = User.builder()
                .id(1L)
                .firstName("Alex")
                .lastName("Jones")
                .birthDate(LocalDate.of(2000, 1, 1))
                .email("simplemail@gmail.com")
                .build();

        when(userService.addUser(any(User.class)))
                .thenReturn(user);

        this.mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void addUserFailed(@Autowired UserService userService
    ) throws Exception {
        User user = User.builder()
                .id(1L)
                .firstName("Alex")
                .email("simplemail@gmail.com")
                .build();

        when(userService.addUser(any(User.class)))
                .thenReturn(user);

        this.mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
