
Summary:
---------

* this egg illustrates maxing out # of virtual threads
    - max on my Mac, default JVM can create 1 million threads (maybe more)
* this egg uses:
    - Java 19 (preview) virtual threads

Build Notes:
------------

* tested with [this jdk](../JDK.version.md)
* tested with [this version](../Maven.version.md) of Maven 
* Gradle does not yet support JDK 19 preview (as of NOV 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)

To Build (using Maven):
-----------------------

* `./mvn-go.sh` will clean, compile, exec 

To Build (using Bash):
----------------------

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` is useful for clean-compile-run cycle

Flight Recorder (with Bash):
----------------

* unclear if there is a way to see virtual threads in JFR (yet)
* use `./run_with_flight_recorder.sh` to generate `flight.jfr`
* install Java Mission Control 8.1.0 from [here](https://adoptopenjdk.net/jmc.html)
* in terminal, run: `open JDK\ Mission\ Control.app/`
* in app, open `flight.jfr`
