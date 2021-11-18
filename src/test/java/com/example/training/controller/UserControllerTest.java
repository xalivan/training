package com.example.training.controller;

import com.example.training.model.UserEntity;
import com.example.training.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class UserControllerTest {
    private final UserServiceImpl service = mock(UserServiceImpl.class);
    private final UserController controller = new UserController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void getAll() throws Exception {
        List<UserEntity> users = new ArrayList<>(Arrays.asList(generateUser(), generateUser(), generateUser()));
        when(service.getAll()).thenReturn(users);

        mockMvc.perform(get("/users").content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$",  is(mapper.writeValueAsString(users))));
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserById() throws Exception {

    }

    @Test
    void put() {
    }


}