
Summary:
---------

* this egg illustrates a small utility class as a convenience wrapper to a custom `StructuredTaskScope`

To Build:
---------

* tested with [this JDK](../jdk.md)
* Gradle does not yet support JDK 19 preview (as of JUN 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯
* parsing the JSON requires [Groovy](https://sdkman.io/sdks#groovy)

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`

To Run:
---------
* in terminal 1: `./run.sh`
    - this runs `Runner.java`
* in terminal 2: `./list-thread-info.sh`
    - this calls `jcmd` and parses the output with `DumpParser.groovy`
