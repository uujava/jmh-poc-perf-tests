package ru.programpark.perf.dao;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by user on 4/3/2016.
 */
public class HashObject implements TestObject, Serializable {
    private final Type type;
    private final Map<String, Object> data;
    public HashObject(Type type) {
        this.type = type;
        data = null;
    }

    public HashObject(Map<String, Object> data) {
        this.type = Type.DATA;
        this.data = data;
    }

    @Override
    public void set(String idx, long v) {
        data.put(idx, v);
    }

    @Override
    public void set(String idx, int v) {
        data.put(idx, v);
    }

    @Override
    public void set(String idx, double v) {
        data.put(idx, v);
    }

    @Override
    public void set(String idx, float v) {
        data.put(idx, v);
    }

    @Override
    public void set(String idx, Object v) {
        data.put(idx, v);
    }

    @Override
    public Object getObject(String idx) {
        return data.get(idx);
    }

    @Override
    public long getLong(String idx) {
        return ((Number)data.get(idx)).longValue();
    }

    @Override
    public double getDouble(String idx) {
        return ((Number)data.get(idx)).doubleValue();
    }

    @Override
    public Type type() {
        return type;
    }
}
