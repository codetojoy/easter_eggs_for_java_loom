
// note:  I no longer own this domain
package net.codetojoy;

public class Worker<T> { 
    T result;

    public Worker(T result) {
        this.result = result;
    }

    public T doWork(String name) throws Exception {
        new Sleeper().sleep(name);
        return result;
    }
}
