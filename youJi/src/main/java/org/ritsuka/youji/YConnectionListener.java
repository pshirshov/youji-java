package org.ritsuka.youji;

import org.jivesoftware.smack.ConnectionListener;

/**
 * Date: 9/30/11
 * Time: 12:05 AM
 */
public class YConnectionListener implements ConnectionListener {
    XMPPWorker worker;

    public YConnectionListener(XMPPWorker a_worker) {
        worker = a_worker;
    }

    @Override
    public void connectionClosed() {
        System.out.println("connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        System.out.println("connectionClosedOnError");
    }

    @Override
    public void reconnectingIn(int seconds) {
        System.out.println("reconnectingIn: " + seconds);
    }

    @Override
    public void reconnectionSuccessful() {
        worker.onLoggedIn();
    }

    @Override
    public void reconnectionFailed(Exception e) {
        System.out.println("reconnectionFailed");
    }
}
