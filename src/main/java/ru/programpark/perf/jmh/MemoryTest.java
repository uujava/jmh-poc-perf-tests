package ru.programpark.perf.jmh;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.util.VMSupport;
import ru.programpark.perf.dao.ArrayInfo;
import ru.programpark.perf.dao.ResultSet;
import ru.programpark.perf.dao.TestObject;
import ru.programpark.perf.dao.TestObjectFactory;

import java.util.Iterator;

/**
 * Created by kozyr on 05.04.2016.
 */
public class MemoryTest {
    static final String[] CLASSES = {"BasicArray", "ObjectArray", "FixedArray", "Field", "Hash", "RubyObject"};
    public static final int AVERAGED_TO = 10000;

    public static void main(String[] args) {
        System.out.println(VMSupport.vmDetails());

        GraphLayout infoLayout = GraphLayout.parseInstance(ArrayInfo.DATA_INFO);

        System.out.println(infoLayout.toFootprint());
        ResultSet resultSet = new ResultSet(1000000);
        resultSet.next();
        // Average to AVERAGED_TO items

        for (int i = 0; i < CLASSES.length; i++) {
            String aClass = CLASSES[i];
            TestObject[] array = new TestObject[AVERAGED_TO];
            TestObjectFactory factory = new TestObjectFactory(aClass);
            for (int j = 0; j < AVERAGED_TO; j++) {
                TestObject object = factory.newInstance();
                array[j] = object;
                resultSet.fillObject(object);
            }
            GraphLayout graphLayout = GraphLayout.parseInstance(array);
            System.out.println(aClass);
//            System.out.println(graphLayout.toFootprint());
            printAveragedLayout(infoLayout, array, graphLayout);
        }
        System.out.println("******* no strings ********");
        for (int i = 0; i < CLASSES.length; i++) {
            String aClass = CLASSES[i];
            TestObject[] array = new TestObject[AVERAGED_TO];
            TestObjectFactory factory = new TestObjectFactory(aClass);
            for (int j = 0; j < AVERAGED_TO; j++) {
                TestObject object = factory.newInstance();
                array[j] = object;
                resultSet.fillObjectNoString(object);
            }
            GraphLayout graphLayout = GraphLayout.parseInstance(array);
            System.out.println(aClass);
//            System.out.println(graphLayout.toFootprint());
            printAveragedLayout(infoLayout, array, graphLayout);
        }
    }

    private static void printAveragedLayout(GraphLayout infoLayout, TestObject[] array, GraphLayout graphLayout) {
        long total = 0;
        GraphLayout instanceLayout = new GraphLayout(array);
        for (Iterator iterator = graphLayout.getClasses().iterator(); iterator.hasNext(); ) {
            Class<?> next = (Class<?>) iterator.next();
            int count = (graphLayout.getClassCounts().count(next) - infoLayout.getClassCounts().count(next)) / AVERAGED_TO;
            int size = (graphLayout.getClassSizes().count(next) - infoLayout.getClassSizes().count(next)) / AVERAGED_TO;
            if (count > 0) {
                instanceLayout.getClasses().add(next);
                instanceLayout.getClassSizes().add(next, size);
                instanceLayout.getClassCounts().add(next, count);
                total += size;
            }
        }
        String minusFutprint = instanceLayout.toFootprint();
        System.out.print("Averaged to " + AVERAGED_TO + " -> " + minusFutprint.substring(0, minusFutprint.length() - 2));
        System.out.println("total: " + total);
        System.out.println();
    }
}
