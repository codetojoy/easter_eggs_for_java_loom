
Summary:
---------

* this egg illustrates structured concurrency
* create N tasks
    - use a custom [StructuredTaskScope](https://download.java.net/java/early_access/loom/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/StructuredTaskScope.html) to define success when X tasks complete 
    - if familiar with `invokeAny()` and `invokeAll()`, this could be considered `invokeSome(n)`
* NOTE: this is highly contrived and NOT production-ready

To Build:
---------

* tested with [this JDK](../JDK.version.md)
* Gradle does not yet support JDK 19 preview (as of NOV 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does: clean, compile, run 
