package BenchmarksProject.ringbuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kozyr on 04.04.2016.
 */
public class ArrayInfo {

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
}
