package org.ritsuka.youji.pm;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
public final class PmActorParametersWrapper {
    private final Message message;
    private Chat chat;

    public PmActorParametersWrapper(final Chat chat, final Message message) {
        this.chat = chat;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public Chat getChat() {
        return chat;
    }
}
