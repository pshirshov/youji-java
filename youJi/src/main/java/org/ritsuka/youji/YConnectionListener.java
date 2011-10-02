package org.ritsuka.youji;

import akka.actor.ActorRef;
import org.jivesoftware.smack.ConnectionListener;
import org.ritsuka.youji.event.ReconnectedEvent;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

/**
 * Date: 9/30/11
 * Time: 12:05 AM
 */
public final class YConnectionListener implements ConnectionListener {
    private final ActorRef worker;
    private final String logId;

    public YConnectionListener(final ActorRef worker, final String logId) {
        this.worker = worker;
        this.logId = logId;
    }

    private Log log() {
        return new Log(LoggerFactory.getLogger(toString()));
    }

    public String toString() {
        return "CL." + logId;
    }

    @Override
    public void connectionClosed() {
    }

    @Override
    public void connectionClosedOnError(final Exception e) {
    }

    @Override
    public void reconnectingIn(final int seconds) {
        log().info("Reconnecting in {}", seconds);
    }

    @Override
    public void reconnectionSuccessful() {
        worker.tell(new ReconnectedEvent());
    }

    @Override
    public void reconnectionFailed(final Exception e) {
        log().error("Reconnection failed: {}", e);
    }
}
