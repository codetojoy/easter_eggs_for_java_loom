
Summary:
---------

* this egg illustrates structured concurrency
* task "foo" and task "bar" are in a race: first one to complete is used; the other is interrupted

To Build:
---------

* tested with [this JDK](../JDK.version.md)
* Gradle does not yet support JDK 19 preview (as of JUL 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does: clean, compile, run 
