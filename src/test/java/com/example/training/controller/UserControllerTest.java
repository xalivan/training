package com.example.training.controller;

import com.example.training.model.UserEntity;
import com.example.training.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
class UserControllerTest {
    private final UserServiceImpl service = mock(UserServiceImpl.class);
    private final UserController controller = new UserController(service);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getUserByIdStatusOk() throws Exception {
        UserEntity userEntity = generateUser();

        when(service.getById(userEntity.getId())).thenReturn(Optional.of(userEntity));

        mockMvc.perform(get("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(userEntity.getId())))
                .andExpect(jsonPath("$.firstName", is(userEntity.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userEntity.getLastName())))
                .andExpect(jsonPath("$.role", is(userEntity.getRole().toString())));
    }

    @Test
    void getUserByIdStatusBadRequest() throws Exception {
        when(service.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putUser() throws Exception {
        UserEntity userEntity = generateUser();
        when(service.put(userEntity)).thenReturn(1);

        String json = "{\n" +
                "        \"id\": 3,\n" +
                "        \"firstName\": \"3333\",\n" +
                "        \"lastName\": \"3333\",\n" +
                "        \"password\": \"$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO\",\n" +
                "        \"role\": \"USER\"\n" +
                "}";

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        List<UserEntity> users = new ArrayList<>(Arrays.asList(generateUser(), generateUser()));

        when(service.getAll()).thenReturn(users);

        String result = mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())))
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<UserEntity> actual = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, UserEntity.class));
//      List<UserEntity> actual = mapper.readValue(result, new TypeReference<List<UserEntity>>() {});
        assertThat(actual, is(users));
    }


    @Test
    void deleteUserOk() throws Exception {
        when(service.delete(1)).thenReturn(true);
        mockMvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserBadRequest() throws Exception {
        when(service.delete(1)).thenReturn(false);
        mockMvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}