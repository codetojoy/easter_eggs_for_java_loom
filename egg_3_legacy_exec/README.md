
Summary:
---------

* this egg illustrates legacy executor/thread pool 

To Build:
---------

* requires JDK 19.ea.22-open via [SDKMan!](https://sdkman.io/)
* Gradle does not yet support JDK 19 preview (as of MAY 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `. ./set_jdk.sh`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does all of the above (minus set_jdk)

Flight Recorder:
----------------

* use `./run_with_flight_recorder.sh` to generate `flight.jfr`
* install Java Mission Control 8.1.0 from [here](https://adoptopenjdk.net/jmc.html)
* in terminal, run: `open JDK\ Mission\ Control.app/`
* in app, open `flight.jfr`
