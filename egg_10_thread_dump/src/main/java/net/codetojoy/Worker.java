
// note:  I no longer own this domain
package net.codetojoy;

class Worker extends BaseWorker { 
    String doWork(String name, String result) throws Exception {
        // sleep in increments for logging 
        int chunkDelayInMillis = 500;
        int i = 0;
        boolean isForever = true;
        
        while (isForever) {
            logInfo(i, name);
            doSleep(chunkDelayInMillis);
        }

        return result;
    }
}
