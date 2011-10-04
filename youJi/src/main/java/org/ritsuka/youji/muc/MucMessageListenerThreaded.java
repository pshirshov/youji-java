package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.handlers.GoogleIt;
import org.ritsuka.youji.handlers.TestMucHandler;

import java.util.ArrayList;
import java.util.List;

import static akka.actor.Actors.actorOf;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public final class MucMessageListenerThreaded implements PacketListener {
    private final ActorRef worker;
    private final MultiUserChat chat;

    public MucMessageListenerThreaded(final ActorRef a_worker, final MultiUserChat a_chat) {
        this.worker = a_worker;
        this.chat = a_chat;
        // TODO: add appropriate plugins
    }

    private List<IMucMsgHandler> instantiateHandlers() {
        List<IMucMsgHandler> handlers = new ArrayList<IMucMsgHandler>();
        handlers.add(new TestMucHandler().setContext(worker, chat));
        handlers.add(new GoogleIt().setContext(worker, chat));
        return handlers;
    }

    @Override
    public void processPacket(final Packet packet) {
        List<IMucMsgHandler> handlers = instantiateHandlers();
        for (IMucMsgHandler handler:handlers) {
            ActorRef actor = actorOf(MucMsgActor.create(handler)).start();
            actor.tell(new MucMsgActorParametersWrapper(packet));
        }
    }
}
