package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
public final class MucMsgActorParametersWrapper {
    private final ActorRef actor;
    private final MultiUserChat chat;
    private final Packet packet;

    public MucMsgActorParametersWrapper(final ActorRef actor,
                                        final MultiUserChat chat,
                                        final Packet packet) {
        this.actor = actor;
        this.chat = chat;
        this.packet = packet;
    }

    public ActorRef getActor() {
        return actor;
    }

    public MultiUserChat getChat() {
        return chat;
    }

    public Packet getMessage() {
        return packet;
    }
}
