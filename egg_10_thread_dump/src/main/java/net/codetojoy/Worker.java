
// note:  I no longer own this domain
package net.codetojoy;

class Worker { 
    String doWork(String name, String result) throws Exception {
        new Sleeper().sleep(name, result);
        return "";
    }
}
