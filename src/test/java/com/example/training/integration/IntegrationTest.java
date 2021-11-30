package com.example.training.integration;

import com.example.training.TrainingApplication;
import com.example.training.controller.UserController;
import com.example.training.model.UserEntity;
import com.example.training.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(classes = {TrainingApplication.class, SecurityConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class IntegrationTest {

    @Autowired
    private UserController controller;
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    void getUserByIdStatusOk() throws Exception {
        UserEntity userEntity = generateUser();
        controller.put(userEntity);

        MockHttpServletResponse response = mockMvc.perform(get("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        UserEntity actual = mapper.readValue(response.getContentAsString(), UserEntity.class);

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(actual, is(userEntity));

    }

    @Test
    void getUserByIdStatusBadRequest() throws Exception {
        UserEntity userEntity = generateUser();
        controller.put(userEntity);
        controller.deleteUser(userEntity.getId());

        MockHttpServletResponse response = mockMvc.perform(get("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    void putUser() throws Exception {
        UserEntity userEntity = generateUser();
        String userAsString = mapper.writeValueAsString(userEntity);

        MockHttpServletResponse response = mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userAsString))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(Integer.valueOf(response.getContentAsString()), is(1));
    }

    @Test
    public void getAllUsers() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(get("/users"))
                .andReturn()
                .getResponse();

        UserEntity[] actual = mapper.readValue(response.getContentAsString(), UserEntity[].class);

        assertThat(actual.length, is(2));
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void deleteUserOk() throws Exception {
        UserEntity userEntity = generateUser();
        controller.put(userEntity);

        MockHttpServletResponse response = mockMvc.perform(delete("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void deleteUserBadRequest() throws Exception {
        UserEntity userEntity = generateUser();
        controller.put(userEntity);
        controller.deleteUser(userEntity.getId());

        MockHttpServletResponse response = mockMvc.perform(delete("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
