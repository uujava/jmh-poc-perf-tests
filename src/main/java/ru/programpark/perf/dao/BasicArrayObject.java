package ru.programpark.perf.dao;

/**
 * Created by user on 4/2/2016.
 */
public class BasicArrayObject implements TestObject {

    private final double[] numeric;
    private final Object[] objects;
    private final ArrayInfo info;

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

}
