package ru.programpark.dao;

/**
 * Created by user on 4/3/2016.
 */
public interface TestObject {
    void set(String idx, long v);

    void set(String idx, int v);

    void set(String idx, double v);

    void set(String idx, float v);

    void set(String idx, Object v);

    Object getObject(String idx);

    long getLong(String idx);

    double getDouble(String idx);

    Type type();
}
