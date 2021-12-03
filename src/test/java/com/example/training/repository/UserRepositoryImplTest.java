package com.example.training.repository;

import com.example.training.model.UserEntity;
import com.example.training.utils.CreatorUser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.ConnectionManager.*;
import static com.example.training.utils.CreatorUser.putUserEntity;
import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@RequiredArgsConstructor
class UserRepositoryImplTest {
    private UserRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        openingConnection();
        repository = new UserRepositoryImpl(dsl);
    }

    @AfterEach
    void tearDown() throws SQLException {
        dsl.deleteFrom(USER_ENTITY).execute();
        connection.close();
        repository = null;
    }

    @Test
    void insert() {
        UserEntity user = generateUser();

        assertThat(repository.insert(user), is(1));
    }

    @Test
    void update() {
        UserEntity user = generateUser();
        CreatorUser.putUserEntity(user);

        assertThat(repository.update(user), is(1));
    }

    @Test
    void delete() {
        UserEntity user = generateUser();
        putUserEntity(user);

        assertThat(repository.delete(user.getId()), is(1));
    }

    @Test
    void findAll() {
        UserEntity user1 = generateUser();
        UserEntity user2 = generateUser();

        putUserEntity(user1);
        putUserEntity(user2);

        List<UserEntity> list = repository.findAll();

        UserEntity[] actual = list.toArray(UserEntity[]::new);

        assertThat(Arrays.asList(actual), containsInAnyOrder(user1, user2));
        assertThat(list.size(), is(2));
    }

    @Test
    void findById() {
        UserEntity user = generateUser();

        putUserEntity(user);

        Optional<UserEntity> userEntity = repository.findById(user.getId());

        assertThat(userEntity, is(Optional.of(user)));
    }

    @Test
    void findOneByLastName() {
        UserEntity user = generateUser();

        putUserEntity(user);

        Optional<UserEntity> userEntity = repository.findOneByLastName(user.getLastName());

        assertThat(userEntity, is(Optional.of(user)));
    }

}