package BenchmarksProject.ringbuffer;

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
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PopulateTest {

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
    public void populateFieldObj(Blackhole bh){
        FieldObject obj = new FieldObject(Type.DATA);
        resultSet.next();
        obj.set("f1", resultSet.getDouble("f1"));
        obj.set("f2", resultSet.getDouble("f2"));
        obj.set("f3", resultSet.getLong("f3"));
        obj.set("f4",resultSet.getLong("f4"));
        obj.set("f5",resultSet.getInt("f5"));
        obj.set("f6",resultSet.getInt("f6"));
        obj.set("f7",resultSet.getInt("f7"));
        obj.set("f8",resultSet.getInt("f8"));
        obj.set("f9",resultSet.getString("f9"));
        obj.set("f10",resultSet.getString("f10"));
        bh.consume(obj);

    }

    @Benchmark
    public void populateFixedArrayObj(Blackhole bh){
        FixedArrayObject obj = new FixedArrayObject(Type.DATA);
        resultSet.next();
        obj.set("f1", resultSet.getDouble("f1"));
        obj.set("f2", resultSet.getDouble("f2"));
        obj.set("f3", resultSet.getLong("f3"));
        obj.set("f4", resultSet.getLong("f4"));
        obj.set("f5", resultSet.getInt("f5"));
        obj.set("f6", resultSet.getInt("f6"));
        obj.set("f7", resultSet.getInt("f7"));
        obj.set("f8", resultSet.getInt("f8"));
        obj.set("f9", resultSet.getString("f9"));
        obj.set("f10", resultSet.getString("f10"));
        bh.consume(obj);
    }

    @Benchmark
    public void populateBasicArrayObj(Blackhole bh){
        BasicArrayObject obj = new BasicArrayObject(Type.DATA, DATA_INFO);
        resultSet.next();
        obj.set("f1", resultSet.getDouble("f1"));
        obj.set("f2", resultSet.getDouble("f2"));
        obj.set("f3", resultSet.getLong("f3"));
        obj.set("f4",resultSet.getLong("f4"));
        obj.set("f5",resultSet.getInt("f5"));
        obj.set("f6",resultSet.getInt("f6"));
        obj.set("f7",resultSet.getInt("f7"));
        obj.set("f8",resultSet.getInt("f8"));
        obj.set("f9",resultSet.getString("f9"));
        obj.set("f10",resultSet.getString("f10"));
        bh.consume(obj);
    }

    @Benchmark
    public void populateHashObj(Blackhole bh){
        HashObject obj = new HashObject(new HashMap<String, Object>(10));
        resultSet.next();
        obj.set("f1", resultSet.getDouble("f1"));
        obj.set("f2", resultSet.getDouble("f2"));
        obj.set("f3", resultSet.getLong("f3"));
        obj.set("f4",resultSet.getLong("f4"));
        obj.set("f5",resultSet.getInt("f5"));
        obj.set("f6",resultSet.getInt("f6"));
        obj.set("f7",resultSet.getInt("f7"));
        obj.set("f8",resultSet.getInt("f8"));
        obj.set("f9",resultSet.getString("f9"));
        obj.set("f10",resultSet.getString("f10"));
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
