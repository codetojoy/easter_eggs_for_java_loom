
Summary:
---------

* https://mail.openjdk.org/pipermail/loom-dev/2022-April/004139.html
* looks like `jcmd` does not yet support 'JavaThread.dump'
* this is just simple but later we will want a composite worker that creates sub-threads
* traced through to : ./src/jdk.management/share/classes/com/sun/management/internal/HotSpotDiagnostic.java
* this egg illustrates structured concurrency

To Build:
---------

* tested with JDK 19.ea.27-open via [SDKMan!](https://sdkman.io/)
* Gradle does not yet support JDK 19 preview (as of JUN 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does: clean, compile, run 
