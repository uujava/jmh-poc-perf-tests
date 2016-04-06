package ru.programpark.perf.dao;

import java.util.HashMap;

/**
 * Created by kozyr on 05.04.2016.
 */
public class TestObjectFactory implements com.lmax.disruptor.EventFactory<ru.programpark.perf.dao.TestObject> {

    private final String className;

    public TestObjectFactory(String className) {
        this.className = className;
    }

    @Override
    public TestObject newInstance() {
        if (className == "Field") {
            return new FieldObject(Type.DATA);
        } else if (className == "BasicArray") {
            return new BasicArrayObject(Type.DATA, ArrayInfo.DATA_INFO);
        } else if (className == "ObjectArray") {
            return new BasicArrayObject(Type.DATA, ArrayInfo.OBJECT_DATA_INFO);
        } else if (className == "FixedArray") {
            return new FixedArrayObject(Type.DATA);
        } else if (className == "Hash") {
            return new HashObject(new HashMap<>(10));
        } else {
            throw new IllegalArgumentException("Unsupported data class: " + className);
        }
    }

    public static final String[] TYPES = new String[]{};
}
