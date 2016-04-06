package ru.programpark.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4,time = 2, timeUnit = TimeUnit.SECONDS)
public class BoxingTest {

    private long longValue;
    private double doubleValue;
    private Double boxedDoubleValue;
    private Long boxedLongValue;

    @Setup(Level.Iteration)
    public void setup(){
        longValue = (long) (Math.random()*Long.MAX_VALUE);
        doubleValue = Math.random();
        boxedDoubleValue = Math.random();
        boxedLongValue = (long) (Math.random()*Long.MAX_VALUE);
    }

    @Benchmark
    public void sumBoxingLongBoxed(Blackhole bh){
        bh.consume(sumBoxing(longValue, boxedLongValue));
    }

    @Benchmark
    public void sumBoxingLong(Blackhole bh){
        bh.consume(sumBoxing(longValue, 12345L));
    }
    @Benchmark
    public void sumBoxingDoubleBoxed(Blackhole bh){
        bh.consume(sumBoxing(doubleValue, boxedDoubleValue));
    }

    @Benchmark
    public void sumBoxingDouble(Blackhole bh){
        bh.consume(sumBoxing(doubleValue, 12345.0));
    }


    @Benchmark
    public void sumLongBoxed(Blackhole bh){
        bh.consume(sum(longValue, boxedLongValue));
    }

    @Benchmark
    public void sumLong(Blackhole bh){
        bh.consume(sum(longValue, 12345L));
    }
    @Benchmark
    public void sumDoubleBoxed(Blackhole bh){
        bh.consume(sum(doubleValue, boxedDoubleValue));
    }

    @Benchmark
    public void sumDouble(Blackhole bh){
        bh.consume(sum(doubleValue, 12345.0));
    }

    public long sumBoxing(Long l1, Long l2){
        return  l1 + l2;
    }

    public double sumBoxing(Double l1, Double l2){
        return  l1 + l2;
    }

    public long sum(long l1, long l2){
        return  l1 + l2;
    }

    public double sum(double l1, double l2){
        return  l1 + l2;
    }
}
