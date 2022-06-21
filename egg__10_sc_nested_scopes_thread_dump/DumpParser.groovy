
import groovy.json.*

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
        threads.each { thread -> 
            def threadId = thread['tid']
            println "thread id: ${threadId} with owner id: ${owner}"
        }
    }
}