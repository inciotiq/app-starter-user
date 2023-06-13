package com.iotiq.user;

import com.iotiq.user.domain.Role;
import com.iotiq.user.domain.User;
import com.iotiq.user.internal.UserRepository;
import com.iotiq.user.messages.UserCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserApplicationTests {


    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void add() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        UserCreateDto userCreateDto = new UserCreateDto();
        String MAIL = "email@email.com";
        String FIRSTNAME = "First";
        String LASTNAME = "Last";
        String USERNAME = "firstlast";
        String PASSWORD = "pass";

        userCreateDto.setEmail(MAIL);
        userCreateDto.setFirstname(FIRSTNAME);
        userCreateDto.setLastname(LASTNAME);
        userCreateDto.setUsername(USERNAME);
        userCreateDto.setPassword(PASSWORD);
        userCreateDto.setRole(Role.ADMIN);

        mockMvc
                .perform(
                        post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(userCreateDto))
                ).andExpect(status().isOk());

        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeCreate + 1);
            User testUser = users.get(users.size() - 1);
            assertThat(testUser.getPersonalInfo().getEmail()).isEqualTo(MAIL);
            assertThat(testUser.getPersonalInfo().getFirstName()).isEqualTo(FIRSTNAME);
            assertThat(testUser.getPersonalInfo().getLastName()).isEqualTo(LASTNAME);
            assertThat(testUser.getAccountSecurity().getRole()).isEqualTo(Role.ADMIN);
        });
    }

    private void assertPersistedUsers(Consumer<List<User>> userAssertion) {
        userAssertion.accept(userRepository.findAll());
    }
}
