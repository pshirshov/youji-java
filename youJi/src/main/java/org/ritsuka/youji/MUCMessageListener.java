package org.ritsuka.youji;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCMessageListener implements PacketListener {
    private XMPPWorker worker;
    private MultiUserChat chat;

    MUCMessageListener(XMPPWorker a_worker, MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    private String id() {
        return worker.connection().getUser() +
               ":" + chat.getRoom() + "/" + chat.getNickname();
    }

    @Override
    public void processPacket(Packet packet) {
        System.out.println("[" + id() + "]" + "Received message: " + packet.toXML());
        try {
            chat.sendMessage("Ходят тут всякие, блядь, пишут хуиту навроде " + packet.toXML());
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
