
Summary:
---------

* code for [this StackOverflow question](https://stackoverflow.com/questions/74464598)

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
    - set DO_SHUTDOWN command-line arg by editing `pom.xml`

To Run (using Bash):
----------------------

* `./run.sh [yes|no]`
    - arg indicates whether or not to shutdown

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`

