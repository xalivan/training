//package com.example.training.integration;
//
//import com.example.training.TrainingApplication;
//import com.example.training.controller.UserController;
//import com.example.training.model.UserEntity;
//import com.example.training.security.SecurityConfig;
//
//import com.example.training.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//import static com.example.training.utils.UserEntityGenerator.generateUser;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//
//@SpringBootTest(classes = {TrainingApplication.class, SecurityConfig.class})
//@AutoConfigureMockMvc
//@ActiveProfiles(value = "test")
//@RequiredArgsConstructor
//public class IntegrationTest {
//
//    private MockMvc mockMvc;
//    private UserController userController;
//    private UserService userService;
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    public void testSave() throws Exception {
////        UserEntity userEntity1 = generateUser();
////        UserEntity userEntity2 = generateUser();
////
////        userService.put(userEntity1);
////        userService.put(userEntity2);
////
////        MockHttpServletResponse response = mockMvc.perform(get("/users"))
////                .andReturn()
////                .getResponse();
////
////        UserEntity[] actual = mapper.readValue(response.getContentAsString(), UserEntity[].class);
////
////        assertThat(Arrays.asList(actual), containsInAnyOrder(userEntity1, userEntity2));
////        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
//
////        UserEntity userEntity = generateUser();
////        String userAsString = mapper.writeValueAsString(userEntity);
////
////        MockHttpServletResponse response = mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
////                        .content(userAsString).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andReturn().getResponse();
////
////        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
////        assertThat(Integer.valueOf(response.getContentAsString()), is(3));
//    }
//}
