//package com.example.training.controller;
//
//import com.example.training.model.UserEntity;
//import com.example.training.service.UserServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static com.example.training.utils.UserEntityGenerator.generateUser;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//@RequiredArgsConstructor
//class UserControllerTest {
//    private final UserServiceImpl service = mock(UserServiceImpl.class);
//    private final UserController controller = new UserController(service);
//    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    void getUserByIdStatusOk() throws Exception {
//        UserEntity userEntity = generateUser();
//
//        when(service.getById(userEntity.getId())).thenReturn(Optional.of(userEntity));
//
//        MockHttpServletResponse response = mockMvc.perform(get("/users/" + userEntity.getId()).contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//
//        UserEntity actual = mapper.readValue(response.getContentAsString(), UserEntity.class);
//
//        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
//        assertThat(actual, is(userEntity));
//    }
//
//    @Test
//    void getUserByIdStatusBadRequest() throws Exception {
//        when(service.getById(1)).thenReturn(Optional.empty());
//
//        MockHttpServletResponse response = mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
//    }
//
//    @Test
//    void putUser() throws Exception {
//        UserEntity userEntity = generateUser();
//        when(service.put(userEntity)).thenReturn(1);
//
//        String userAsString = mapper.writeValueAsString(userEntity);
//        MockHttpServletResponse response = mockMvc.perform(put("/users")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(userAsString))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
//    }
//
//    @Test
//    void getAll() throws Exception {
//        UserEntity userEntity1 = generateUser();
//        UserEntity userEntity2 = generateUser();
//
//        when(service.getAll()).thenReturn(List.of(userEntity1, userEntity2));
//
//        MockHttpServletResponse response = mockMvc.perform(get("/users"))
//                .andReturn()
//                .getResponse();
//
//        UserEntity[] actual = mapper.readValue(response.getContentAsString(), UserEntity[].class);
//
//        assertThat(Arrays.asList(actual), containsInAnyOrder(userEntity1, userEntity2));
//        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
//    }
//
//    @Test
//    void deleteUserOk() throws Exception {
//        when(service.delete(1)).thenReturn(true);
//        MockHttpServletResponse response = mockMvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
//    }
//
//    @Test
//    void deleteUserBadRequest() throws Exception {
//        when(service.delete(1)).thenReturn(false);
//        MockHttpServletResponse response = mockMvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
//    }
//}