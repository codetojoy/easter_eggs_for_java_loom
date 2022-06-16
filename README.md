
### easter_eggs_for_java_loom

- examples re: [Project Loom](https://openjdk.org/projects/loom/) in JDK 19
- usage of *egg* here is intended as an [SSCCE](http://sscce.org/); **not** a [hidden feature](https://en.wikipedia.org/wiki/Easter_egg_(media))

### Notes

* includes examples for [virtual threads](https://openjdk.java.net/jeps/425), a "preview" feature in JDK 19
* includes examples for [structured concurrency](https://openjdk.java.net/jeps/428), which is "incubator" in JDK 19 
    - we use `sc` for "structured concurrency" in the folder names
* see README for each example

### Resources

* JEP 425 [Virtual Threads](https://openjdk.java.net/jeps/425)
* JEP 428 [Structured Concurrency](https://openjdk.java.net/jeps/428)
* [Java 19 Virtual Threads - JEP Café](https://www.youtube.com/watch?v=lKSSBvRDmTg)
* [Project Loom: Modern Scalable Concurrency for the Java Platform](https://www.youtube.com/watch?v=EO9oMiL1fFo) by Ron Pressler
    - 12m00s : "codes like sync, scales like async"
    - 12m34s : excellent discussion on Thread vs async/await in various languages
    - 30m44s : great slides illustrating 1:1 versus M:N
    - 37m34s : Little's Law
* [Project Loom: Revolution in concurrency or obscure implementation detail?](https://www.youtube.com/watch?v=n_XRUljffu0) by Tomasz Nurkiewicz
    - contrarian view
* [Project Loom - A Friend or Foe of Reactive?](https://www.youtube.com/watch?v=YwG04UZP2a0) by Oleh Dokuka and Andrii Rodionov
    - contrarian view
    - esp. near 19m20s
* [AMA About the Java Language](https://www.youtube.com/watch?v=9si7gK94gLo) by Brian Goetz and Nicolai Parlog
    - near 19m10s, "Loom will kill Reactive programming"
    - reddit thread [here](https://www.reddit.com/r/programming/comments/oxsnqg/brian_goetz_i_think_project_loom_is_going_to_kill/)
* [Project Loom Q&A with Ron Pressler](https://www.youtube.com/watch?v=cAHW96omBAc)
    - esp. near 55m25s
* [Project Loom C5M](https://github.com/ebarlas/project-loom-c5m)
* blog post: [Notes on structured concurrency, or: Go statement considered harmful](https://vorpus.org/blog/notes-on-structured-concurrency-or-go-statement-considered-harmful) by Nathaniel J. Smith

    - 5 million concurrent connections
* [Conway's Game of Life with virtual threads](https://github.com/ebarlas/game-of-life-csp)
