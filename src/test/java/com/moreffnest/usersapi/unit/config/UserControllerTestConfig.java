package com.moreffnest.usersapi.unit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moreffnest.usersapi.hateoas.UserModelAssembler;
import com.moreffnest.usersapi.models.User;
import com.moreffnest.usersapi.services.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PagedResourcesAssembler;

@TestConfiguration
public class UserControllerTestConfig {
    @MockBean
    public UserService userService;

    @Bean
    public UserModelAssembler userModelAssembler() {
        return new UserModelAssembler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  //to support LocalDateTime type
        return objectMapper;
    }
}
