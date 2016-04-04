package ru.programpark.dao;

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
            numeric[i] = Math.random();
            strings[i] = Double.toHexString(numeric[i]);
        }
    }

    int index = 0;
    int count = 0;

    public boolean next(){
        index++;
        return true;
    }

    public double getDouble(String s){
        return numeric[index%count];
    }

    public long getLong(String s){
        return (long) numeric[index%count];
    }

    public int getInt(String s){
        return (int) numeric[index%count];
    }

    public String getString(String s){
        return strings[index%count];
    }

}
