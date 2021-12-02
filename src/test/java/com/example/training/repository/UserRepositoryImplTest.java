package com.example.training.repository;


import com.example.training.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.training.jooq.tables.Role.ROLE;
import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@RequiredArgsConstructor
class UserRepositoryImplTest {
    Connection connection;
    DSLContext dsl;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/test", "admin", "1111");
        dsl = DSL.using(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        dsl.deleteFrom(USER_ENTITY).execute();
        dsl = null;
        connection.close();
        connection = null;

    }

    @Test
    void insert() {
        UserEntity user = generateUser();
        int execute = dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
        assertThat(execute, is(1));
    }

    @Test
    void update() {
        UserEntity user = generateUser();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();

        int execute = dsl.update(USER_ENTITY)
                .set(USER_ENTITY.ID, user.getId())
                .set(USER_ENTITY.FIRST_NAME, user.getFirstName())
                .set(USER_ENTITY.LAST_NAME, user.getLastName())
                .set(USER_ENTITY.PASSWORD, user.getPassword())
                .set(USER_ENTITY.ROLE, user.getRole().getId())
                .where(USER_ENTITY.ID.eq(user.getId()))
                .execute();
        assertThat(execute, is(1));
    }

    @Test
    void delete() {
        UserEntity user = generateUser();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();

        int execute = dsl.delete(USER_ENTITY)
                .where(USER_ENTITY.ID.eq(user.getId()))
                .execute();
        assertThat(execute, is(1));

    }

    @Test
    void findAll() {
        UserEntity user1 = generateUser();
        UserEntity user2 = generateUser();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user1.getId(), user1.getFirstName(), user1.getLastName(), user1.getPassword(), user1.getRole().getId())
                .execute();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user2.getId(), user2.getFirstName(), user2.getLastName(), user2.getPassword(), user2.getRole().getId())
                .execute();

        List<UserEntity> list = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .fetchInto(UserEntity.class);
        

        UserEntity[] actual = list.toArray(UserEntity[]::new);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user1, user2));
        assertThat(list.size(), is(2));
    }

    @Test
    void findById() {
        UserEntity user = generateUser();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();

        Optional<UserEntity> userEntity = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(user.getId()))
                .fetchOptionalInto(UserEntity.class);

          assertThat(userEntity, is(Optional.of(user)));
    }

    @Test
    void findOneByLastName() {
        UserEntity user = generateUser();

        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();

        Optional<UserEntity> userEntity = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.LAST_NAME.equal(user.getLastName()))
                .fetchOptionalInto(UserEntity.class);

        assertThat(userEntity, is(Optional.of(user)));
    }
}