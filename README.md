jmh-poc-perf-tests
================
DESCRIPTION
-----------------

Gather assorted tests used to validate different perfomrance assumptions
output JVM bytecode.

SYNOPSIS
-----------------
    # build
    mvn clean package
    # run all behnchmarks
    java -jar target\benchmarks.jar -bm thrpt,sample -prof gc
    #help for options
    java -jar target\benchmarks.jar -h
    #print memory layout:
    java -cp target\benchmarks.jar ru.programpark.perf.jmh.MemoryTest

REQUIREMENTS
-----------------

* Java 1.8 or higher.
* Maven 3.0 or higher.
