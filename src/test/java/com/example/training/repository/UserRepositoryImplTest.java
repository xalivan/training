package com.example.training.repository;

import com.example.training.model.UserEntity;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.training.jooq.tables.Role.ROLE;
import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.TestDatabaseConnectionManager.getConnection;
import static com.example.training.utils.UserEntityGenerator.generateUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

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

        assertThat(repository.insert(user), is(1));
        assertThat(repository.findOneByLastName(user.getLastName()).get(), is(user));
    }

    @Test
    void update() {
        UserEntity user = generateUser();
        insertUserEntityTest(user);

        assertThat(repository.findOneByLastName(user.getLastName()).get(), is(user));
        assertThat(repository.update(user), is(1));
    }

    @Test
    void delete() {
        UserEntity user = generateUser();
        insertUserEntityTest(user);

        assertThat(repository.delete(user.getId()), is(1));
        assertThat(getUserEntityByIdTest(user.getId()).isEmpty(), is(true));
    }

    @Test
    void findAll() {
        UserEntity user1 = generateUser();
        UserEntity user2 = generateUser();
        insertUserEntityTest(user1);
        insertUserEntityTest(user2);

        assertThat(repository.findAll(), containsInAnyOrder(user1, user2));
    }

    @Test
    void findById() {
        UserEntity user = generateUser();
        insertUserEntityTest(user);

        assertThat(repository.findOneByLastName(user.getLastName()).get(), is(user));
    }

    @Test
    void findOneByLastName() {
        UserEntity user = generateUser();
        insertUserEntityTest(user);

        assertThat(repository.findOneByLastName(user.getLastName()).get(), is(user));
    }

    private Optional<UserEntity> getUserEntityByIdTest(int id) {
        return dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(id))
                .fetchOptionalInto(UserEntity.class);
    }

    private void insertUserEntityTest(UserEntity user) {
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
    }
}