
Summary:
---------

* simple client to send messages over a socket to the server in `../server`
* NOTE: this is highly contrived and NOT production-ready

To Build:
---------

* tested with JDK 19.ea.26-open via [SDKMan!](https://sdkman.io/)
* Gradle does not yet support JDK 19 preview (as of JUN 2022). Check [here](https://docs.gradle.org/current/userguide/compatibility.html)
* unknown if Maven can be used ¯\_(ツ)_/¯

useful commands:

* `sdk env`
    - SDKMan! will set JDK to value in `.sdkmanrc`
* `./clean.sh`
* `./compile.sh`
* `./run.sh`
* note `./go.sh` does: clean, compile, run 
