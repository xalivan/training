package com.example.training.repository;

import com.example.training.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.training.jooq.tables.Role.ROLE;
import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.TestDatabaseConnectionManager.getConnection;
import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@RequiredArgsConstructor
class UserRepositoryImplTest {
    private final DSLContext dsl = getConnection();
    private final UserRepository repository = new UserRepositoryImpl(dsl);

    @AfterEach
    void tearDown() {
        dsl.deleteFrom(USER_ENTITY).execute();
    }

    @Test
    void insert() {
        UserEntity user = generateUser();

        int insert = repository.insert(user);
        Optional<UserEntity> userEntity = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(user.getId()))
                .fetchOptionalInto(UserEntity.class);
        UserEntity[] actual = userEntity.stream().toArray(UserEntity[]::new);

        assertThat(insert, is(1));
        assertThat(Arrays.asList(actual), containsInAnyOrder(user));
    }

    @Test
    void update() {
        UserEntity user = generateUser();
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
        Optional<UserEntity> userEntity = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(user.getId()))
                .fetchOptionalInto(UserEntity.class);
        UserEntity[] actual = userEntity.stream().toArray(UserEntity[]::new);
        int update = repository.update(user);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user));
        assertThat(update, is(1));
    }

    @Test
    void delete() {
        UserEntity user = generateUser();
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
        int delete = repository.delete(user.getId());
        Optional<UserEntity> userEntity = dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(user.getId()))
                .fetchOptionalInto(UserEntity.class);

        assertThat(delete, is(1));
        assertThat(userEntity.isEmpty(), is(true));
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
        List<UserEntity> list = repository.findAll();
        UserEntity[] actual = list.toArray(UserEntity[]::new);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user1, user2));
    }

    @Test
    void findById() {
        UserEntity user = generateUser();
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
        Optional<UserEntity> userEntity = repository.findById(user.getId());
        UserEntity[] actual = userEntity.stream().toArray(UserEntity[]::new);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user));
    }

    @Test
    void findOneByLastName() {
        UserEntity user = generateUser();
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
        Optional<UserEntity> userEntity = repository.findOneByLastName(user.getLastName());
        UserEntity[] actual = userEntity.stream().toArray(UserEntity[]::new);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user));
    }
}