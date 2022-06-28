
Summary:
---------

* this egg illustrates structured concurrency
* two tasks: `foo` and `bar`
    - can be configured to succeed or fail
* NOTE
    - happy path: one task takes X seconds; the other takes Y seconds
    - when one task is configured to fail, the other is interrupted

To Build:
---------

* tested with [this JDK](../JDK.version.md)
* Gradle does not yet support JDK 19 preview (as of JUN 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does: clean, compile, run
