
Summary:
---------

* this egg illustrates legacy executor/thread pool 

Build Notes:
------------

* tested with [this jdk](../JDK.version.md)
* tested with [this version](../Maven.version.md) of Maven 
* Gradle does not yet support JDK 19 preview (as of NOV 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)

To Run (using Gradle):
---------------------

* `./gradle-run.sh`

To Run (using Maven):
---------------------

* `./mvn-run.sh`

To Run (using Bash):
----------------------

* `./run.sh`

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`

Flight Recorder (with Bash):
----------------

* use `./run_with_flight_recorder.sh` to generate `flight.jfr`
* install Java Mission Control 8.1.0 from [here](https://adoptopenjdk.net/jmc.html)
* in terminal, run: `open JDK\ Mission\ Control.app/`
* in app, open `flight.jfr`
