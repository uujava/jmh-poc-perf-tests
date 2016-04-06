package ru.programpark.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/6/2016.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
public class LookupTest {

    private String string1;
    private String string2;
//    private IdentityHashMap<String, String> identityHashMap;
//    private IdentityHashMap<Object, Object> hashMap;

    @Setup(Level.Iteration)
    public void baseline() {
        string1 = Double.toHexString(Math.random()).intern();
        string2 = new String(string1);
//        identityHashMap = new IdentityHashMap<>();
//        identityHashMap.put(string1, string1);
//        hashMap = new IdentityHashMap<>();
//        hashMap.put(string1, string1);
    }

    @Benchmark
//    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void stringEqualsNoJit(Blackhole bh) {
        bh.consume(string1.equals(string2));
    }

    @Benchmark
    public void stringInternEquals(Blackhole bh) {
        bh.consume(string1 == string2.intern());
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void stringAreSameNoJit(Blackhole bh) {
        bh.consume(string1 == string2);
    }

//    @Benchmark
//    public void stringLookup(Blackhole bh) {
//        bh.consume(hashMap.get(string2));
//        bh.consume(hashMap.get(string1));
//    }
//
//    @Benchmark
//    public void stringIdentityLookup(Blackhole bh) {
//        bh.consume(identityHashMap.get(string2));
//        bh.consume(identityHashMap.get(string1));
//    }

}
