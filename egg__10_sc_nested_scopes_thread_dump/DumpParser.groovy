
import groovy.json.*

// This is a relatively quick/dirty program to parse the JSON output
// for jcmd with the command `Thread.dump_to_file`.
//
// Based on looking at the JSON output, it is hardcoded to find "thread flocks",
// which apparently represent threads used in a scope.
// 
// ---------- main 

def jsonFile = args[0]
def text = new File(jsonFile).getText()
def json = new JsonSlurper().parseText(text)

def threadContainers = json['threadDump']['threadContainers']

threadContainers.each { threadContainer ->
    def container = threadContainer['container']

    if (container =~ /.*ThreadFlock.*/) {
        def owner = threadContainer['owner']
        def threads = threadContainer['threads']
        def threadCount = threadContainer['threadCount'] as int
        def qualifier = ""
        if (threadCount == 1) { qualifier = "(likely Foo, created by main)      " }
        if (threadCount == 2) { qualifier = "(likely a Bar, created by Foo)     " }
        if (threadCount == 3) { qualifier = "(likely a Worker, created by a Bar)" }

        threads.each { thread -> 
            def threadId = thread['tid']
            println "TRACER ${qualifier} thread id: ${threadId} with owner id: ${owner}"
        }
    }
}
