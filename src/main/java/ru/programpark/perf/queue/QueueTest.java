package ru.programpark.perf.queue;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.programpark.perf.dao.ResultSet;
import ru.programpark.perf.dao.TestObject;
import ru.programpark.perf.dao.TestObjectFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Group)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
public class QueueTest {

    private ResultSet resultSet;
    private ArrayBlockingQueue<TestObject> queue;

    @Param({"Field", "BasicArray", "FixedArray", "ObjectArray", "Hash"})
    String className;
    private TestObjectFactory objectFactory;

    @Setup
    public void setup() {
        resultSet = new ResultSet(1000000);
        objectFactory = new TestObjectFactory(className);
        queue = new ArrayBlockingQueue<TestObject>(100000);
    }

    @TearDown
    public void teardown() {
        resultSet.next();
        try {
            // To avoid locked producer
            queue.put(objectFactory.newInstance());
        } catch (Exception ex) {
            //skip
        }
        resultSet = null;
    }

    @Benchmark
    @Group("queue")
    @GroupThreads(1)
    public void produce(Blackhole bh) {
        TestObject object = objectFactory.newInstance();
        resultSet.next();
        resultSet.fillObject(object);
        try {
            queue.put(object);
        } catch (InterruptedException ex) {
            System.out.println("interrupted = " + ex);
        }
    }

    @Benchmark
    @Group("baseline")
    public void baseline(Blackhole bh) {
        TestObject obj = objectFactory.newInstance();
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    @Benchmark
    @Group("queue")
    @GroupThreads(1)
    public void consume(Blackhole bh) {
        try {
            bh.consume(queue.poll(200, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(QueueTest.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .shouldDoGC(true)
                .warmupIterations(4)
                .forks(1)
                .mode(Mode.All)
                .param("className", "Hash")
                .exclude("baseline")
                .jvmArgsAppend("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005")
                .measurementIterations(4)
                .build();
        new Runner(opt).run();
    }
}
