
### easter_eggs_for_java_loom

- examples re: [Project Loom](https://openjdk.org/projects/loom/) 
- usage of *egg* here is intended as an [SSCCE](http://sscce.org/); **not** a [hidden feature](https://en.wikipedia.org/wiki/Easter_egg_(media))

### Notes, MAY 2026

* MAY 2026: `main` branch uses Java 26
* eggs 1-9 work with Bash, Gradle, or Maven
    * Gradle 9.5.1
    * Maven 3.9.11
* see also branches: `jdk-21`, `jdk-19` (original)
    * branch [jdk-19](https://github.com/codetojoy/easter_eggs_for_java_loom/tree/jdk-19) has eggs 10, 11, 12 which are now obsolete

### Index

* egg_1_max_p_threads
    * demo max # of platform threads
* egg_2_max_v_threads
    * demo max # of virtual threads
* egg_3_legacy_exec
    * illustrates legacy executor/thread pool 
* egg_4_virtual_exec
    * illustrates virtual threads via ExecutorService
* egg_5_sc_invoke_all
    * structured concurrency
    * two tasks: `foo` and `bar`
        - can be configured to succeed or fail
        - when one task is configured to fail, the other is interrupted
* egg_6_sc_invoke_any
    * structured concurrency
    * task `foo` and task `bar` are in a race: first one to complete is used; the other is interrupted
* egg_7_sc_deadline
    * illustrates structured concurrency
    * task `foo` and task `bar` take too long and exceed a deadline
        - both are interrupted
* egg_8_sc_custom_invoke_some
    * this egg illustrates structured concurrency
    * create `T` tasks
        - use a custom `StructuredTaskScope.Joiner` to define success when `n` tasks complete 
        - if familiar with `invokeAny()` and `invokeAll()`, this could be considered `invokeSome(n)`
    * NOTE: highly contrived and NOT production-ready
* egg_9_sc_fan_in
    * simple `client` to send messages over a socket to a `server`
    * NOTE: highly contrived and NOT production-ready
* others
    * egg__11*, egg__12
    * see `jdk-19` branch
    * these may not have been moved to `jdk-21` and definitely not `jdk-25`


