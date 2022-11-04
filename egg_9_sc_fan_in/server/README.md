
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


