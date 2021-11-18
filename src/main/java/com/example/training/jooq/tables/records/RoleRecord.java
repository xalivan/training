/*
 * This file is generated by jOOQ.
 */
package com.example.training.jooq.tables.records;


import com.example.training.jooq.tables.Role;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RoleRecord extends UpdatableRecordImpl<RoleRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.role.role_id</code>.
     */
    public void setRoleId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.role.role_id</code>.
     */
    public Integer getRoleId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.role.role</code>.
     */
    public void setRole(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.role.role</code>.
     */
    public String getRole() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Role.ROLE.ROLE_ID;
    }

    @Override
    public Field<String> field2() {
        return Role.ROLE.ROLE_;
    }

    @Override
    public Integer component1() {
        return getRoleId();
    }

    @Override
    public String component2() {
        return getRole();
    }

    @Override
    public Integer value1() {
        return getRoleId();
    }

    @Override
    public String value2() {
        return getRole();
    }

    @Override
    public RoleRecord value1(Integer value) {
        setRoleId(value);
        return this;
    }

    @Override
    public RoleRecord value2(String value) {
        setRole(value);
        return this;
    }

    @Override
    public RoleRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RoleRecord
     */
    public RoleRecord() {
        super(Role.ROLE);
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    public RoleRecord(Integer roleId, String role) {
        super(Role.ROLE);

        setRoleId(roleId);
        setRole(role);
    }
}