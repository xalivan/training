package com.example.training.utils;

import com.example.training.model.UserEntity;

import static com.example.training.jooq.tables.UserEntity.USER_ENTITY;
import static com.example.training.utils.ConnectionManager.dsl;

public class CreatorUser {

    public static void putUserEntity(UserEntity user) {
        dsl.insertInto(USER_ENTITY, USER_ENTITY.ID, USER_ENTITY.FIRST_NAME, USER_ENTITY.LAST_NAME, USER_ENTITY.PASSWORD, USER_ENTITY.ROLE)
                .values(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getRole().getId())
                .execute();
    }
}
