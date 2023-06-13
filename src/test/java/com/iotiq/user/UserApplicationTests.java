package com.iotiq.user;

import com.iotiq.user.domain.Role;
import com.iotiq.user.messages.UserCreateDto;
import com.iotiq.user.messages.UserDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserApplicationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void add() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setEmail("email@email.com");
        userCreateDto.setFirstname("First");
        userCreateDto.setLastname("Last");
        userCreateDto.setUsername("firstlast");
        userCreateDto.setPassword("pass");
        userCreateDto.setRole(Role.ADMIN);

        UserDto createdUser = restTemplate
                .postForObject("/api/v1/users", userCreateDto, UserDto.class);
        assertNotNull(createdUser);
        assertEquals("email@email.com", createdUser.getEmail());
    }
}
