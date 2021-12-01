package com.example.training.integration;

import com.example.training.TrainingApplication;
import com.example.training.controller.UserController;
import com.example.training.model.UserEntity;
import com.example.training.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static com.example.training.jooq.tables.Role.ROLE;
import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = {TrainingApplication.class, SecurityConfig.class})
@AutoConfigureMockMvc
//@ContextConfiguration(locations = {"changelog/db.changelog-master.xml"})
@ActiveProfiles(value = "test")
public class IntegrationTest {


    public static void main(String[] args) throws SQLException {
//        Connection connection;
//        DSLContext ctx;

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "admin", "1111");
        DSLContext ctx = DSL.using(connection);



        List<UserEntity> userEntities = ctx.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .fetchInto(UserEntity.class);

        System.out.println(userEntities.toString());
    }

    @BeforeEach
    public void setup() throws Exception {


    }

//    @AfterEach
//    public void after() throws Exception {
//        ctx = null;
//        connection.close();
//        connection = null;
//    }

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
                .andReturn().getResponse();

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
//        String userName = "admin";
//        String password = "1111";
//        String url = "jdbc:postgresql://10.2.1 20210110:8080/test";
//        Connection conn = DriverManager.getConnection(url, userName, password);

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

//    @Test
//    public void mcveTest() {
//        TestRecord result =
//                ctx.insertInto(TEST)
//                        .columns(TEST.VALUE)
//                        .values(42)
//                        .returning(TEST.ID)
//                        .fetchOne();
//
//        result.refresh();
//        assertEquals(42, (int) result.getValue());
//    }
}
