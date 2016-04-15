package ru.programpark.perf.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by user on 4/2/2016.
 */
public class BasicArrayObject implements TestObject, Serializable {

    private final double[] numeric;
    private final Object[] objects;
    private final transient ArrayInfo info;

    public BasicArrayObject(Type type, ArrayInfo info) {
        this.info = info;
        numeric = new double[info.getMaxDouble()];
        objects = new Object[info.getMaxObject()];
        set("type", type);
    }

    @Override
    public void set(String idx, long v) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            numeric[_idx] = v;
        } else {
            objects[_idx] = v;
        }

    }

    @Override
    public void set(String idx, int v) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            numeric[_idx] = v;
        } else {
            objects[_idx] = v;
        }

    }

    @Override
    public void set(String idx, double v) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            numeric[_idx] = v;
        } else {
            objects[_idx] = v;
        }
    }

    @Override
    public void set(String idx, float v) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            numeric[_idx] = v;
        } else {
            objects[_idx] = v;
        }

    }

    @Override
    public void set(String idx, Object v) {
        int _idx = idx(idx);
        objects[_idx - numeric.length] = v;
    }

    @Override
    public Object getObject(String idx) {
        int _idx = idx(idx);
        if (_idx < numeric.length) {
            return numeric[_idx];
        } else {
            return objects[_idx - numeric.length];
        }
    }

    @Override
    public long getLong(String idx) {
        return ((Number) getObject(idx)).longValue();
    }

    protected int idx(String idx) {
        return info.idx(idx);
    }

    @Override
    public double getDouble(String idx) {
        return ((Number) getObject(idx)).doubleValue();
    }

    @Override
    public Type type() {
        return (Type) getObject("type");
    }

    @Override
    public void writeSelf(DataOutputStream bos) {
        try {
            bos.write(numeric.length); // write numeric size first
            for (int pos = 0; pos < numeric.length; pos++) {
                bos.writeDouble(numeric[pos]);
            }
            // TODO generify!
            bos.write(objects.length); // write numeric size first
            if (objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    // write obj size per each object
                    Object object = objects[i];
                    writePrimitive(bos, object);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
