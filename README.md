jmh-poc-perf-tests
================
DESCRIPTION
-----------------

Gather assorted tests used to validate different perfomrance assumptions
output JVM bytecode.

SYNOPSIS
-----------------

    mvn clean package
    java -jar target\benchmarks.jar -bm thrpt,sample -f 1 -i 4 -tu us -wi 3
    or
    java -jar target\benchmarks.jar -h

REQUIREMENTS
-----------------

* Java 1.8 or higher.
* Maven 3.0 or higher.
