
// note:  I no longer own this domain
package net.codetojoy;

class Worker<T> { 
    T result;

    Worker(T result) {
        this.result = result;
    }

    T doWork(String name) throws Exception {
        new Sleeper().sleep(name);
        return result;
    }
}
