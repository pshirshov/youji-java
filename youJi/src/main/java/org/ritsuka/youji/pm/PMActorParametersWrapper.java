package org.ritsuka.youji.pm;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
public final class PMActorParametersWrapper {
    private final ActorRef actor;
    private final Chat chat;
    private final Message message;

    public PMActorParametersWrapper(final ActorRef actor,
                                    final Chat chat,
                                    final Message message) {
        this.actor = actor;
        this.chat = chat;
        this.message = message;
    }

    public ActorRef getActor() {
        return actor;
    }

    public Chat getChat() {
        return chat;
    }

    public Message getMessage() {
        return message;
    }
}
