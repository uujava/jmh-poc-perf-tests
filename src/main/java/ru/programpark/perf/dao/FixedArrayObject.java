package ru.programpark.perf.dao;

import java.io.Serializable;

/**
 * Created by user on 4/2/2016.
 */
public class FixedArrayObject implements TestObject, Serializable {

    private final double[] numeric;
    private final Object[] objects;
    private Type type;

    public FixedArrayObject(Type type){
        this.type = type;
        if(type == Type.DATA) {
            numeric = new double[8];
            objects = new Object[2];
        } else {
            numeric = null;
            objects = null;
        }
    }

    @Override
    public void set(String idx, long v){
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, int v){
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, double v){
        numeric[idx(idx)] = v;
    }

    @Override
    public  void set(String idx, float v){
        numeric[idx(idx)] = v;
    }

    @Override
    public void set(String idx, Object v){
        objects[idx(idx)-numeric.length] = v;
    }

    @Override
    public Object getObject(String idx){
        int _idx = idx(idx);
        if(_idx < numeric.length){
            return numeric[_idx];
        } else {
            return objects[_idx - numeric.length];
        }
    }

    @Override
    public long getLong(String idx){
        return (long) numeric[idx(idx)];
    }

    protected int idx(String idx) {
        if (idx == "f1") {
            return 0;
        } else if (idx  == "f2") {
            return 1;
        } else if (idx  == "f3") {
            return 2;
        } else if (idx  == "f4") {
            return 3;
        } else if (idx == "f5") {
            return 4;
        } else if (idx  == "f6") {
            return 5;
        } else if (idx  == "f7") {
            return 6;
        } else if (idx  == "f8") {
            return 7;
        } else if (idx  == "f9") {
            return 8;
        } else if (idx  == "f10") {
            return 9;
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    @Override
    public double getDouble(String idx){
        return numeric[idx(idx)];
    }

    @Override
    public Type type() {
        return type;
    }


}
