package ru.programpark.perf.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by user on 4/3/2016.
 */
public class FieldObject implements TestObject, Serializable {

    private double f1;
    private double f2;
    private long f3;
    private long f4;
    private int f5;
    private int f6;
    private int f7;
    private int f8;
    private Object f9;
    private Object f10;

    @Override
    public Type type() {
        return type;
    }

    private Type type;

    public FieldObject(Type type) {
        this.type = type;
    }

    @Override
    public void set(String idx, long v) {
        if (idx == "f1") {
            f1 = v;
        } else if (idx == "f2") {
            f2 = v;
        } else if (idx == "f3") {
            f3 = v;
        } else if (idx == "f4") {
            f4 = v;
        } else if (idx == "f5") {
            f5 = (int) v;
        } else if (idx == "f6") {
            f6 = (int) v;
        } else if (idx == "f7") {
            f7 = (int) v;
        } else if (idx == "f8") {
            f8 = (int) v;
        } else if (idx == "f9") {
            f9 = new Long(v);
        } else if (idx == "f10") {
            f10 = new Long(v);
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    @Override
    public void set(String idx, int v) {
        if (idx == "f1") {
            f1 = v;
        } else if (idx == "f2") {
            f2 = v;
        } else if (idx == "f3") {
            f3 = v;
        } else if (idx == "f4") {
            f4 = v;
        } else if (idx == "f5") {
            f5 = (int) v;
        } else if (idx == "f6") {
            f6 = (int) v;
        } else if (idx == "f7") {
            f7 = (int) v;
        } else if (idx == "f8") {
            f8 = (int) v;
        } else if (idx == "f9") {
            f9 = new Integer(v);
        } else if (idx == "f10") {
            f10 = new Integer(v);
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }

    }

    @Override
    public void set(String idx, double v) {
        if (idx == "f1") {
            f1 = v;
        } else if (idx == "f2") {
            f2 = v;
        } else if (idx == "f3") {
            f3 = (long) v;
        } else if (idx == "f4") {
            f4 = (long) v;
        } else if (idx == "f5") {
            f5 = (int) v;
        } else if (idx == "f6") {
            f6 = (int) v;
        } else if (idx == "f7") {
            f7 = (int) v;
        } else if (idx == "f8") {
            f8 = (int) v;
        } else if (idx == "f9") {
            f9 = v;
        } else if (idx == "f10") {
            f10 = v;
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    @Override
    public void set(String idx, float v) {
        if (idx == "f1") {
            f1 = v;
        } else if (idx == "f2") {
            f2 = v;
        } else if (idx == "f3") {
            f3 = (long) v;
        } else if (idx == "f4") {
            f4 = (long) v;
        } else if (idx == "f5") {
            f5 = (int) v;
        } else if (idx == "f6") {
            f6 = (int) v;
        } else if (idx == "f7") {
            f7 = (int) v;
        } else if (idx == "f8") {
            f8 = (int) v;
        } else if (idx == "f9") {
            f9 = v;
        } else if (idx == "f10") {
            f10 = v;
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    @Override
    public void set(String idx, Object v) {
        if (idx == "f1") {
            f1 = (double) v;
        } else if (idx == "f2") {
            f2 = (double) v;
        } else if (idx == "f3") {
            f3 = (long) v;
        } else if (idx == "f4") {
            f4 = (long) v;
        } else if (idx == "f5") {
            f5 = (int) v;
        } else if (idx == "f6") {
            f6 = (int) v;
        } else if (idx == "f7") {
            f7 = (int) v;
        } else if (idx == "f8") {
            f8 = (int) v;
        } else if (idx == "f9") {
            f9 = v;
        } else if (idx == "f10") {
            f10 = v;
        } else {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
    }

    @Override
    public Object getObject(String idx) {
        return null;
    }

    @Override
    public long getLong(String idx) {
        return 0;
    }

    @Override
    public double getDouble(String idx) {
        return 0;
    }

    @Override
    public void writeSelf(DataOutputStream bos) {
        try {
            bos.writeDouble(f1);
            bos.writeDouble(f2);
            bos.writeDouble(f3);
            bos.writeDouble(f4);
            bos.writeDouble(f5);
            bos.writeDouble(f6);
            bos.writeDouble(f7);
            bos.writeDouble(f8);
            writePrimitive(bos, f9);
            writePrimitive(bos, f10);
            writePrimitive(bos, type);
        } catch (IOException e) {
            throw new RuntimeException("unable to serialize: " + this + " " + e, e);
        }
    }
}
