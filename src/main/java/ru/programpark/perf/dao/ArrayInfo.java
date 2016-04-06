package ru.programpark.perf.dao;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by kozyr on 04.04.2016.
 */
public class ArrayInfo {
    public final static ArrayInfo STATE_INFO;
    public final static ArrayInfo DATA_INFO;
    public final static ArrayInfo OBJECT_DATA_INFO;
    private int maxDouble = 0;
    private int maxObject = 0;

    public int getMaxObject() {
        return maxObject;
    }

    public int getMaxDouble() {
        return maxDouble;
    }

    private Map<String, Integer> idx = new HashMap<>();

    public void addNumberField(String idx) {
        if(maxObject>0) throw new IllegalStateException("numeric field " + idx +" must be registered before object fields: ("+ maxDouble +":" + maxObject+ ") all: " + idx);
        this.idx.put(idx, maxDouble++);
    }

    public void addObjectField(String idx) {
        this.idx.put(idx, maxDouble + maxObject++);
    }

    public int idx(String idx) {
        return this.idx.get(idx);
    }

    @Override
    public String toString() {
        return "ArrayInfo{" +
                "maxDouble=" + maxDouble +
                ", maxObject=" + maxObject +
                ", idx=" + idx +
                '}';
    }

    static {
        STATE_INFO = new ArrayInfo();
        STATE_INFO.addObjectField("type");
        DATA_INFO = new ArrayInfo();
        for (int i = 1; i <= 8; i++) {
            DATA_INFO.addNumberField("f"+i);
        }
        for (int i = 9; i <= 10; i++) {
            DATA_INFO.addObjectField("f" + i);
        }
        DATA_INFO.addObjectField("type");

        OBJECT_DATA_INFO = new ArrayInfo();
        for (int i = 1; i <= 10; i++) {
            OBJECT_DATA_INFO.addObjectField("f" + i);
        }
        OBJECT_DATA_INFO.addObjectField("type");

    }
}
