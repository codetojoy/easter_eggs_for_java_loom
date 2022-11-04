
Summary:
---------

* this egg illustrates structured concurrency
* task "foo" and task "bar" take too long and exceed a deadline
    - both are interrupted
Build Notes:
------------

* tested with [this jdk](../JDK.version.md)
* tested with [this version](../Gradle.version.md) of Gradle 
* tested with [this version](../Maven.version.md) of Maven 

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

