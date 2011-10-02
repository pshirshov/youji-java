package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
public class MucMsgActorParametersWrapper {
    private final ActorRef actor;
    private final MultiUserChat chat;
    private final Packet packet;

    public MucMsgActorParametersWrapper(ActorRef actor, MultiUserChat chat, Packet packet) {
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
