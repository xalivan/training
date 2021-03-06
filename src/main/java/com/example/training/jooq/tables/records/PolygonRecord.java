/*
 * This file is generated by jOOQ.
 */
package com.example.training.jooq.tables.records;


import com.example.training.jooq.tables.Polygon;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PolygonRecord extends UpdatableRecordImpl<PolygonRecord> implements Record3<Integer, Double, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.polygon.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.polygon.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.polygon.square</code>.
     */
    public void setSquare(Double value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.polygon.square</code>.
     */
    public Double getSquare() {
        return (Double) get(1);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    public void setGeometry(Object value) {
        set(2, value);
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    public Object getGeometry() {
        return get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Double, Object> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, Double, Object> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Polygon.POLYGON.ID;
    }

    @Override
    public Field<Double> field2() {
        return Polygon.POLYGON.SQUARE;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    @Override
    public Field<Object> field3() {
        return Polygon.POLYGON.GEOMETRY;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Double component2() {
        return getSquare();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    @Override
    public Object component3() {
        return getGeometry();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Double value2() {
        return getSquare();
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    @Override
    public Object value3() {
        return getGeometry();
    }

    @Override
    public PolygonRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PolygonRecord value2(Double value) {
        setSquare(value);
        return this;
    }

    /**
     * @deprecated Unknown data type. Please define an explicit {@link org.jooq.Binding} to specify how this type should be handled. Deprecation can be turned off using {@literal <deprecationOnUnknownTypes/>} in your code generator configuration.
     */
    @Deprecated
    @Override
    public PolygonRecord value3(Object value) {
        setGeometry(value);
        return this;
    }

    @Override
    public PolygonRecord values(Integer value1, Double value2, Object value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PolygonRecord
     */
    public PolygonRecord() {
        super(Polygon.POLYGON);
    }

    /**
     * Create a detached, initialised PolygonRecord
     */
    public PolygonRecord(Integer id, Double square, Object geometry) {
        super(Polygon.POLYGON);

        setId(id);
        setSquare(square);
        setGeometry(geometry);
    }
}
