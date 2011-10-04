package org.ritsuka.youji.muc;

import org.jivesoftware.smack.packet.Message;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
final class MucMsgActorParametersWrapper {
    private final Message message;

    public MucMsgActorParametersWrapper(final Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
