package ru.programpark.perf.jmh;

import org.nustaq.serialization.FSTConfiguration;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.programpark.perf.dao.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
public class SerializationTest {
    @Param({"Field", "BasicArray", "FixedArray", "ObjectArray", "Hash"})
    String className;
    private ResultSet resultSet;
    private TestObjectFactory factory;

    @Setup(Level.Iteration)
    public void setup() {
        resultSet = new ResultSet(1000000);
        factory = new TestObjectFactory(className);
    }

    @Benchmark
    public void fstMarshal(FSTSerializer serializer, Blackhole bh){
        TestObject object = factory.newInstance();
        resultSet.fillObjectNoString(object);
        bh.consume(serializer.dump(object));
    }

    @Benchmark
    public void baseline(FSTSerializer serializer, Blackhole bh) {
        TestObject object = factory.newInstance();
        resultSet.fillObject(object);
        bh.consume(object);
    }
    @Benchmark
    public void fstMarshalRegistered(RegisteredFSTSerializer serializer, Blackhole bh){
        TestObject object = factory.newInstance();
        resultSet.fillObject(object);
        bh.consume(serializer.dump(object));
    }

    @Benchmark
    public void fstMarshalRegisteredWithString(RegisteredFSTSerializer serializer, Blackhole bh){
        TestObject object = factory.newInstance();
        resultSet.fillObject(object);
        bh.consume(serializer.dump(object));
    }

//    @Benchmark
//    public void javaMarshalDump(){
//
//    }
//
//    @Benchmark
//    public void arrayMarshalDump(){
//
//    }

    @State(Scope.Thread)
    public static class FSTSerializer{

        private FSTConfiguration fst;

        @Setup(Level.Iteration)
        public void setup(){
            fst = FSTConfiguration.createDefaultConfiguration();
        }


        public byte[] dump(TestObject object) {
            return fst.asByteArray(object);
        }
    }

    @State(Scope.Thread)
    public static class RegisteredFSTSerializer{

        private FSTConfiguration fst;

        @Setup(Level.Iteration)
        public void setup(){
            fst = FSTConfiguration.createDefaultConfiguration();
            fst.registerClass(BasicArrayObject.class, FixedArrayObject.class, FieldObject.class, HashObject.class, Long.class, Double.class, HashMap.class, Integer.class);
            fst.setShareReferences(false);
        }


        public byte[] dump(TestObject object) {
            return fst.asByteArray(object);
        }
    }
}
