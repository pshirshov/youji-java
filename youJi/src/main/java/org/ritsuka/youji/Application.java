package org.ritsuka.youji;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Application {
    public static void main(final String[] args1) {
        System.out.println("Hi");
        // Create a connection to the jabber.org server.
        Connection conn1 = new XMPPConnection("jabber.org");
        try {
            conn1.connect();
            conn1.login("huipizda", "pizdahui", "hooita");
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        /*conn1.connect();

        // Create a connection to the jabber.org server on a specific port.
        ConnectionConfiguration config = new ConnectionConfiguration("jabber.org", 5222);
        Connection conn2 = new XMPPConnection(config);
        conn2.connect();*/
    }
}
