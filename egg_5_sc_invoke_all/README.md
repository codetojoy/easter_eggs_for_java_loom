
Summary:
---------

* this egg illustrates structured concurrency
* two tasks: `foo` and `bar`
    - can be configured to succeed or fail
* NOTE
    - happy path: one task takes X seconds; the other takes Y seconds
    - when one task is configured to fail, the other is interrupted

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

