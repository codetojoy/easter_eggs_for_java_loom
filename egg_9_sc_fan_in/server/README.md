
Summary:
---------

* fan-in server as illustrated in [JEP 428](https://openjdk.java.net/jeps/428)
* see `../client` for code to send messages to localhost on port [5150](https://en.wikipedia.org/wiki/5150_(album))
* TODO
    - parse messages such as `delay: 1000`, then sleep for duration specified
* NOTE: this is highly contrived and NOT production-ready

Build Notes:
------------

* tested with [this jdk](../JDK.version.md)
* tested with [this version](../Maven.version.md) of Maven 
* Gradle does not yet support JDK 19 preview (as of NOV 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)

To Build (using Maven):
---------------------

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

