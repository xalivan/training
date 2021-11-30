/*
 * This file is generated by jOOQ.
 */
package com.example.training.jooq.tables.records;


import com.example.training.jooq.tables.UserEntity;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class UserEntityRecord extends UpdatableRecordImpl<UserEntityRecord> implements Record5<Integer, String, String, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user_entity.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_entity.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.user_entity.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_entity.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.user_entity.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user_entity.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.user_entity.password</code>.
     */
    public void setPassword(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user_entity.password</code>.
     */
    public String getPassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user_entity.role</code>.
     */
    public void setRole(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user_entity.role</code>.
     */
    public Integer getRole() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, String, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, String, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return UserEntity.USER_ENTITY.ID;
    }

    @Override
    public Field<String> field2() {
        return UserEntity.USER_ENTITY.FIRST_NAME;
    }

    @Override
    public Field<String> field3() {
        return UserEntity.USER_ENTITY.LAST_NAME;
    }

    @Override
    public Field<String> field4() {
        return UserEntity.USER_ENTITY.PASSWORD;
    }

    @Override
    public Field<Integer> field5() {
        return UserEntity.USER_ENTITY.ROLE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFirstName();
    }

    @Override
    public String component3() {
        return getLastName();
    }

    @Override
    public String component4() {
        return getPassword();
    }

    @Override
    public Integer component5() {
        return getRole();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFirstName();
    }

    @Override
    public String value3() {
        return getLastName();
    }

    @Override
    public String value4() {
        return getPassword();
    }

    @Override
    public Integer value5() {
        return getRole();
    }

    @Override
    public UserEntityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UserEntityRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public UserEntityRecord value3(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public UserEntityRecord value4(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UserEntityRecord value5(Integer value) {
        setRole(value);
        return this;
    }

    @Override
    public UserEntityRecord values(Integer value1, String value2, String value3, String value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserEntityRecord
     */
    public UserEntityRecord() {
        super(UserEntity.USER_ENTITY);
    }

    /**
     * Create a detached, initialised UserEntityRecord
     */
    public UserEntityRecord(Integer id, String firstName, String lastName, String password, Integer role) {
        super(UserEntity.USER_ENTITY);

        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setRole(role);
    }
}
