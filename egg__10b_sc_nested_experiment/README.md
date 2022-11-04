
Summary:
---------

* this egg illustrates parent-child relationships in structured concurrency 
    - see "Tree Structure" [here](https://download.java.net/java/early_access/loom/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/StructuredTaskScope.html)
* the idea is to create a small hierarchy of threads in a `StructuredTaskScope`, and then use `jcmd` to interrogate the JVM
* specifically:
    - `Runner` program writes process id to `pid.txt`
    - `Runner` main thread creates a `Foo` thread
    - `Foo` thread creates 2 `Bar` threads
    - each `Bar` thread creates 3 `Worker` threads 
    - all threads sleep forever, so we can interrogate; the scope has a deadline of 2 minutes 
    - Bash scripts are used to create a thread dump (in JSON), using `pid.txt`
    - a Groovy program parses the JSON and prints out salient thread ids

To Build (using Gradle):
---------------------

* `./gradle-build.sh`

To Build (using Maven):
---------------------

* `./mvn-build.sh`

To Build (using Bash):
----------------------

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`

To Run:
---------
* in terminal 1: 
    - Gradle: `gradle-run.sh`
    - Maven: `mvn-run.sh`
    - Bash: `./run.sh`
    - this runs `Runner.java`
* in terminal 2: `./list-thread-info.sh`
    - this calls `jcmd` and parses the output with `DumpParser.groovy`

