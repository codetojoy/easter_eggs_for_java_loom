
// note:  I no longer own this domain
package net.codetojoy;

class Worker { 
    void doWork(String name) throws Exception {
        new Sleeper().sleep(name);
    }
}
