
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
    - this example uses [this constructor](https://docs.oracle.com/en/java/javase/19/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/StructuredTaskScope.html#%3Cinit%3E(java.lang.String,java.util.concurrent.ThreadFactory)) so that the Groovy program can parse for specific container names

To Build:
---------

* tested with [this JDK](../JDK.version.md)
* Gradle does not yet support JDK 19 preview (as of NOV 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯
* parsing the JSON requires [Groovy](https://sdkman.io/sdks#groovy)

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`

To Run:
---------
* in terminal 1: `./run.sh`
    - this runs `Runner.java`
* in terminal 2: `./list-thread-info.sh`
    - this calls `jcmd` and parses the output with `DumpParser.groovy`
