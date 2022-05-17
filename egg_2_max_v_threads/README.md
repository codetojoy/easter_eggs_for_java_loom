
Summary:
---------

* this egg illustrates maxing out # of virtual threads
    - max on my Mac, default JVM can create 1 million threads (maybe more)
* this egg uses:
    - Java 19 (preview) virtual threads

To Build:
---------

* requires JDK 19.ea.22-open via [SDKMan!](https://sdkman.io/)
* Gradle does not yet support JDK 19 preview (as of MAY 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does all of the above

Flight Recorder:
----------------

* unclear if there is a way to see virtual threads in JFR (yet)
* use `./run_with_flight_recorder.sh` to generate `flight.jfr`
* install Java Mission Control 8.1.0 from [here](https://adoptopenjdk.net/jmc.html)
* in terminal, run: `open JDK\ Mission\ Control.app/`
* in app, open `flight.jfr`