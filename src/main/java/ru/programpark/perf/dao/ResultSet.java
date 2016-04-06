package ru.programpark.perf.dao;

/**
 * Created by user on 4/3/2016.
 */
public class ResultSet {
    private final double[] numeric;
    private final String[] strings;

    public ResultSet(int count) {
        this.count = count;
        this.numeric = new double[count];
        this.strings = new String[count];
        for (int i = 0; i < count; i++) {
            numeric[i] = Math.random()*Integer.MAX_VALUE;
            strings[i] = Double.toHexString(numeric[i]);
        }
    }

    int index = 0;
    int count = 0;

    public boolean next() {
        index = (index + 1) % count;
        return true;
    }

    public boolean last() {
        index = (index + 1) % count;
        return true;
    }

    public double getDouble(String s) {
        next();
        return numeric[index];
    }

    public long getLong(String s) {
        next();
        return (long) numeric[index];
    }

    public int getInt(String s) {
        next();
        return (int) numeric[index];
    }

    public String getString(String s) {
        next();
        return strings[index];
    }

    public void fillObject(TestObject obj) {
        obj.set("f1", getDouble("f1"));
        obj.set("f2", getDouble("f2"));
        obj.set("f3", getLong("f3"));
        obj.set("f4", getLong("f4"));
        obj.set("f5", getInt("f5"));
        obj.set("f6", getInt("f6"));
        obj.set("f7", getInt("f7"));
        obj.set("f8", getInt("f8"));
        obj.set("f9", getString("f9"));
        obj.set("f10", getString("f10"));
    }

    public void fillObjectNoString(TestObject obj) {
        obj.set("f1", getDouble("f1"));
        obj.set("f2", getDouble("f2"));
        obj.set("f3", getLong("f3"));
        obj.set("f4", getLong("f4"));
        obj.set("f5", getInt("f5"));
        obj.set("f6", getInt("f6"));
        obj.set("f7", getInt("f7"));
        obj.set("f8", getInt("f8"));
    }

}
