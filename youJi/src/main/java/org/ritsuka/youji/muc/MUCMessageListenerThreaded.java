package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCMessageListenerThreaded implements PacketListener {
    private ActorRef worker;
    private MultiUserChat chat;

    private Logger log()
    {
        return LoggerFactory.getLogger(toString());
    }

    public MUCMessageListenerThreaded(ActorRef a_worker, MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    @Override
    public void processPacket(Packet packet) {
        MUCMessageListenerAk sendback = new MUCMessageListenerAk();
        sendback.packet = packet;
        sendback.chat = chat;
        worker.tell(sendback);
    }
}
