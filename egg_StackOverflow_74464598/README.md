
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
* tested with [this version](../Gradle.version.md) of Gradle 
* tested with [this version](../Maven.version.md) of Maven 

To Run (using Gradle):
---------------------

* `./gradle-run.sh [yes|no]`
    - arg indicates whether or not to shutdown

To Run (using Maven):
---------------------

* `./mvn-run.sh`
    - set DO_SHUTDOWN command-line arg in `pom.xml`

To Run (using Bash):
----------------------

* `./run.sh [yes|no]`
    - arg indicates whether or not to shutdown

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`

