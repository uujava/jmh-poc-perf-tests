package ru.programpark.perf.basic;

import ru.programpark.perf.dao.*;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import static ru.programpark.perf.dao.ArrayInfo.*;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4,time = 2, timeUnit = TimeUnit.SECONDS)
public class PopulateTest {

    private ResultSet resultSet;

    @Setup
    public void setup(){
        resultSet = new ResultSet(1000000);
    }

    @Benchmark
    public void baseline(Blackhole bh){
        resultSet.next();
        bh.consume(resultSet.getDouble("f1"));
        bh.consume(resultSet.getDouble("f2"));
        bh.consume(resultSet.getLong("f3"));
        bh.consume(resultSet.getLong("f4"));
        bh.consume(resultSet.getInt("f5"));
        bh.consume(resultSet.getInt("f6"));
        bh.consume(resultSet.getInt("f7"));
        bh.consume(resultSet.getInt("f8"));
        bh.consume(resultSet.getString("f9"));
        bh.consume(resultSet.getString("f10"));
    }

    @Benchmark
    public void getDouble(Blackhole bh){
        resultSet.next();
        bh.consume(resultSet.getDouble("f1"));
    }

    @Benchmark
    public void getString(Blackhole bh){
        resultSet.next();
        bh.consume(resultSet.getString("f1"));
    }

    @Benchmark
    public void getLong(Blackhole bh){
        resultSet.next();
        bh.consume(resultSet.getLong("f1"));
    }

    @Benchmark
    public void populateFieldObj(Blackhole bh){
        FieldObject obj = new FieldObject(Type.DATA);
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    @Benchmark
    public void populateFixedArrayObj(Blackhole bh){
        FixedArrayObject obj = new FixedArrayObject(Type.DATA);
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    @Benchmark
    public void populateBasicArrayObj(Blackhole bh){
        BasicArrayObject obj = new BasicArrayObject(Type.DATA, DATA_INFO);
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    @Benchmark
    public void populateHashObj(Blackhole bh){
        HashObject obj = new HashObject(new HashMap<>(10));
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PopulateTest.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .shouldDoGC(true)
                .warmupIterations(4)
                .forks(1)
                .mode(Mode.All)
                .measurementIterations(4)
                .build();
        new Runner(opt).run();
    }
}
