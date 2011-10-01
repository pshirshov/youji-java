package org.ritsuka.youji;

import org.ritsuka.youji.event.AppShutdownEvent;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Date: 9/10/11
 * Time: 5:58 PM
 */
public class ShutdownHook extends Thread {
    private  CountDownLatch latch;

    public ShutdownHook(CountDownLatch latch) {
        this.latch = latch;
    }

    private Log log() {
        return new Log(LoggerFactory.getLogger("APP"));
    }
    public final void run() {
        log().info("Shutting down...");

        Supervisor.instance().tell(new AppShutdownEvent());
        try {
            latch.await();
        } catch (InterruptedException e) {
            log().error("Shutdown hook error: {}", e);
        }
    }
}