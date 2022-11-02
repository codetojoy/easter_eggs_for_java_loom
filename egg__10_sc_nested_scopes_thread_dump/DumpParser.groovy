
// This is a relatively quick/dirty program to parse the JSON output
// for jcmd with the command `Thread.dump_to_file`.
//
// Based on looking at the JSON output, it is hardcoded to find "thread flocks",
// which apparently represent threads used in a scope.
// 
// ---------- main 

def jsonFile = args[0]
def text = new File(jsonFile).getText()
def json = new groovy.json.JsonSlurper().parseText(text)

def threadContainers = json['threadDump']['threadContainers']

threadContainers.findAll { it['container'] =~ /.*ThreadFlock.*/ }.each { threadContainer ->
    def owner = threadContainer['owner']
    def threads = threadContainer['threads']
    def qualifier = ""
    def container = threadContainer['container']
    if (container =~ /^main.*/) { qualifier = "(Foo, created by main)    " }
    if (container =~ /^foo.*/) { qualifier =  "(Bar, created by Foo)     " }
    if (container =~ /^bar-.*/) { qualifier =  "(Worker, created by a Bar)" }

    threads.each { thread -> 
        def threadId = thread['tid']
        println "TRACER ${qualifier} thread id: ${threadId} with owner id: ${owner}"
    }
}
