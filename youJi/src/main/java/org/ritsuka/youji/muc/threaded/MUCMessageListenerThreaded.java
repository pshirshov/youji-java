package org.ritsuka.youji.muc.threaded;

import akka.actor.ActorRef;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.muc.MUCMessageListenerAk;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCMessageListenerThreaded implements PacketListener {
    private ActorRef worker;
    private MultiUserChat chat;

    public MUCMessageListenerThreaded(ActorRef a_worker, MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    @Override
    public void processPacket(Packet packet) {
        MUCMessageListenerAk sendback = new MUCMessageListenerAk();
        sendback.setPacket(packet);
        sendback.setChat(chat);
        worker.tell(sendback);
    }
}
