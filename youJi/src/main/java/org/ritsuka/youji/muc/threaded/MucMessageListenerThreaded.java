package org.ritsuka.youji.muc.threaded;

import akka.actor.ActorRef;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.muc.IMucMsgHandler;
import org.ritsuka.youji.muc.MucMsgActor;
import org.ritsuka.youji.muc.MucMsgActorParametersWrapper;
import org.ritsuka.youji.muc.TestMucHandler;

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
    private final List<IMucMsgHandler> handlers = new ArrayList<IMucMsgHandler>();

    public MucMessageListenerThreaded(final ActorRef a_worker,
                                      final MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;

        // TODO: add appropriate plugins
        handlers.add(new TestMucHandler());
    }

    @Override
    public void processPacket(final Packet packet) {
        for (IMucMsgHandler handler:handlers) {
            ActorRef actor = actorOf(MucMsgActor.create(handler)).start();
            actor.tell(new MucMsgActorParametersWrapper(worker, chat, packet));
        }
    }
}
