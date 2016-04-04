package ru.programpark.basic;

import ru.programpark.dao.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CreateTest {

    private static final ArrayInfo STATE_INFO;
    private static final ArrayInfo DATA_INFO;

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

//        System.out.println("STATE_INFO = " + STATE_INFO);
//        System.out.println("DATA_INFO = " + DATA_INFO);
    }

    public void setup(){

    }
    @Benchmark
    public void createTypeFieldObj(Blackhole bh){
        bh.consume(new FieldObject(Type.FIN));
    }

    @Benchmark
    public void createDataFieldObj(Blackhole bh){
        bh.consume(new FieldObject(Type.DATA));
    }

    @Benchmark
    public void createTypeFixedArrayObj(Blackhole bh){
        bh.consume(new FixedArrayObject(Type.FIN));
    }

    @Benchmark
    public void createDataFixedArrayObj(Blackhole bh){
        bh.consume(new FixedArrayObject(Type.DATA));
    }

    @Benchmark
    public void createTypeBasicArrayObj(Blackhole bh){
        bh.consume(new BasicArrayObject(Type.FIN, STATE_INFO));
    }

    @Benchmark
    public void createDataBasicArrayObj(Blackhole bh){
        bh.consume(new BasicArrayObject(Type.DATA, DATA_INFO));
    }

    @Benchmark
    public void createTypeHashObj(Blackhole bh){
        bh.consume(new HashObject(Type.FIN));
    }

    @Benchmark
    public void createDataHashObj(Blackhole bh){
        bh.consume(new HashObject(new HashMap<String, Object>(10)));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CreateTest.class.getSimpleName())
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
