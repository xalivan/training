package com.example.training.repository;

import com.example.training.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.training.jooq.tables.Role.ROLE;
import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dsl;

    @Override
    public int insert(UserEntity user) {
        return dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
    }

    @Override
    public int update(UserEntity user) {
        return dsl.update(USER_ENTITY)
                .set(USER_ENTITY.ID, user.getId())
                .set(USER_ENTITY.FIRST_NAME, user.getFirstName())
                .set(USER_ENTITY.LAST_NAME, user.getLastName())
                .set(USER_ENTITY.PASSWORD, user.getPassword())
                .set(USER_ENTITY.ROLE, user.getRole().getId())
                .where(USER_ENTITY.ID.eq(user.getId()))
                .execute();
    }

    @Override
    public int delete(int id) {
        return dsl.delete(USER_ENTITY)
                .where(USER_ENTITY.ID.eq(id))
                .execute();
    }

    @Override
    public List<UserEntity> findAll() {
        return dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .fetchInto(UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findById(int id) {
                return dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.ID.eq(id))
                .fetchOptionalInto(UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findOneByLastName(String lastName) {
        return dsl.select(USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, ROLE.ROLE_)
                .from(USER_ENTITY)
                .join(ROLE).on(USER_ENTITY.ROLE.eq(ROLE.ROLE_ID))
                .where(USER_ENTITY.LAST_NAME.equal(lastName))
                .fetchOptionalInto(UserEntity.class);
    }
}
