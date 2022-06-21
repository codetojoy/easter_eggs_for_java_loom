
Summary:
---------

* this egg illustrates structured concurrency
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

To Build:
---------

* tested with JDK 19.ea.27-open via [SDKMan!](https://sdkman.io/)
* Gradle does not yet support JDK 19 preview (as of JUN 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

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
