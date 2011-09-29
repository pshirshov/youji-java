package org.ritsuka.youji;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCMessageListener implements PacketListener {
    String id = null;
    public MUCMessageListener(String a_id) {
        id = a_id;
    }

    @Override
    public void processPacket(Packet packet) {
        System.out.println("[" + id + "]" +"Received message: " + packet.toXML());
    }
}
