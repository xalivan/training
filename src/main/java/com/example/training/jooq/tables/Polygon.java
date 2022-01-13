/*
 * This file is generated by jOOQ.
 */
package com.example.training.jooq.tables;


import com.example.training.jooq.Keys;
import com.example.training.jooq.Public;
import com.example.training.jooq.tables.records.PolygonRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Polygon extends TableImpl<PolygonRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.polygon</code>
     */
    public static final Polygon POLYGON = new Polygon();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PolygonRecord> getRecordType() {
        return PolygonRecord.class;
    }

    /**
     * The column <code>public.polygon.id</code>.
     */
    public final TableField<PolygonRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.polygon.square</code>.
     */
    public final TableField<PolygonRecord, Double> SQUARE = createField(DSL.name("square"), SQLDataType.DOUBLE, this, "");

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    public final TableField<PolygonRecord, Object> GEOMETRY = createField(DSL.name("geometry"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"public\".\"geometry\""), this, "");

    private Polygon(Name alias, Table<PolygonRecord> aliased) {
        this(alias, aliased, null);
    }

    private Polygon(Name alias, Table<PolygonRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.polygon</code> table reference
     */
    public Polygon(String alias) {
        this(DSL.name(alias), POLYGON);
    }

    /**
     * Create an aliased <code>public.polygon</code> table reference
     */
    public Polygon(Name alias) {
        this(alias, POLYGON);
    }

    /**
     * Create a <code>public.polygon</code> table reference
     */
    public Polygon() {
        this(DSL.name("polygon"), null);
    }

    public <O extends Record> Polygon(Table<O> child, ForeignKey<O, PolygonRecord> key) {
        super(child, key, POLYGON);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PolygonRecord, Integer> getIdentity() {
        return (Identity<PolygonRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PolygonRecord> getPrimaryKey() {
        return Keys.POLYGON_PKEY;
    }

    @Override
    public List<UniqueKey<PolygonRecord>> getKeys() {
        return Arrays.<UniqueKey<PolygonRecord>>asList(Keys.POLYGON_PKEY);
    }

    @Override
    public Polygon as(String alias) {
        return new Polygon(DSL.name(alias), this);
    }

    @Override
    public Polygon as(Name alias) {
        return new Polygon(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Polygon rename(String name) {
        return new Polygon(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Polygon rename(Name name) {
        return new Polygon(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Double, Object> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
