### easter_eggs_for_java_loom

* some basic examples for Project Loom, which is preview in JDK 19 (as of 17-MAY-2022) 
* usage of *egg* here is intended as a small, example in the spirit of [SSCCE](http://sscce.org/); **not** a [hidden feature](https://en.wikipedia.org/wiki/Easter_egg_(media))
* items:
    - egg_1_max_p_threads: illustrate max # of platform threads	
    - egg_2_max_v_threads: illustrate max # of virtual threads (far more)	
    - egg_3_legacy_exec: demo legacy Executor (thread-pool)
    - egg_4_virtual_exec: demo virtual-thread Executor (no pool, but has the facade of the legacy API)
