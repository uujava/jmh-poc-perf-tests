package ru.programpark.perf.jmh;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.infra.Control;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.programpark.perf.dao.ResultSet;
import ru.programpark.perf.dao.TestObject;
import ru.programpark.perf.dao.TestObjectFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 4/3/2016.
 */
@State(Scope.Group)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
public class DisruptorTest {

    private ResultSet resultSet;
    private RingBuffer<TestObject> ring;

    @Param({"Field", "BasicArray", "FixedArray", "ObjectArray", "Hash"})
    String className;
    private Disruptor<TestObject> disruptor;
    private Consumer consumer;
    private TestObjectFactory factory;

    @Setup(Level.Iteration)
    public void setup() {
        resultSet = new ResultSet(1000000);
        // Build a disruptor and start it.
        factory = new TestObjectFactory(className);
        disruptor = new Disruptor<>(factory, 1024 * 128, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new SleepingWaitStrategy());
        ring = disruptor.start();
        SequenceBarrier sequenceBarrier = ring.newBarrier();
        Sequence seq = new Sequence(SingleProducerSequencer.INITIAL_CURSOR_VALUE);
        ring.addGatingSequences(seq);
        consumer = new Consumer(sequenceBarrier, seq, ring);
//        System.out.println("setup = " + ring.getCursor() + " gate:  " + ring.getMinimumGatingSequence());
    }

    @TearDown(Level.Iteration)
    public void teardown() {
//        System.out.println("teardown cursor: " + ring.getCursor() + " gate:  " + ring.getMinimumGatingSequence() + " diff: " + (ring.getCursor() - ring.getMinimumGatingSequence()));
        try {
            long next = ring.tryNext();
            consumer.alert();
            ring.publish(next);
            next = ring.tryNext();
            ring.publish(next);
        } catch(InsufficientCapacityException ex) {
            System.out.println("ring is full at teardown: "  + ex);
        } finally {
//            System.out.println("halt disruptor: ");
            disruptor.halt();
//            System.out.println("done");
            disruptor = null;
            ring = null;
        }
    }

    @Benchmark
    @Group("baseline")
    public void baseline(Blackhole bh) {
        TestObject obj = factory.newInstance();
        resultSet.next();
        resultSet.fillObject(obj);
        bh.consume(obj);
    }

    @Benchmark
    @Group("ring")
    @GroupThreads(1)
    public void produce(Blackhole bh, Control ctrl) {
        try {
            long next = ring.tryNext();
            TestObject object = ring.get(next);
            resultSet.next();
            resultSet.fillObject(object);
            ring.publish(next);
        }catch (InsufficientCapacityException ex){
            try{
              Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
              System.out.println("produce interrupted");
            }
        }
    }

    @Benchmark
    @Group("ring")
    @GroupThreads(1)
    public void consume(Blackhole bh, Control ctrl) {
        if (ctrl.stopMeasurement) return;
        bh.consume(consumer.next());
        consumer.processed();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DisruptorTest.class.getSimpleName())
                .addProfiler(GCProfiler.class)
                .shouldDoGC(true)
                .warmupIterations(4)
                .forks(1)
                .mode(Mode.All)
                .param("className", "Field")
                .exclude("baseline")
//                .jvmArgsAppend("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005")
                .measurementIterations(4)
                .build();
        new Runner(opt).run();
    }

    private class Consumer {

        private final SequenceBarrier barrier;
        private final RingBuffer<TestObject> ring;
        private final Sequence seq;
        private long next = 0;
        private long available = -1;

        public Consumer(SequenceBarrier barrier, Sequence seq, RingBuffer<TestObject> ring) {
            this.barrier = barrier;
            this.ring = ring;
            this.seq = seq;
            next = seq.get();
            available = seq.get();
        }

        TestObject next() {
            if (available < next) {
                try {
                    available = barrier.waitFor(next);
                } catch (AlertException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            return ring.get(next);
        }

        void processed() {
            if (next == available) {
                seq.set(next);
            }
            next = next + 1;
        }

        public void alert() {
            barrier.alert();
        }
    }

}
