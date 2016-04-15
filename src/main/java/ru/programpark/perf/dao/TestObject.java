package ru.programpark.perf.dao;

import java.io.DataOutputStream;
import java.io.IOException;

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

    void writeSelf(DataOutputStream out);

    // TODO implement header and proper type handling
    default void writePrimitive(DataOutputStream bos, Object object) throws IOException {
        if (object instanceof String) {
            bos.writeChars((String) object);
        } else if (object instanceof Number) {
            bos.writeDouble(((Number) object).doubleValue());
        } else if (object instanceof Type) {
            bos.write(((Type) object).ordinal());
        } else if (object == null) {
            bos.writeChars(""); //????
        } else {
            throw new IllegalArgumentException("Unsupported primitive: " + object + " " + this);
        }
    }

}
