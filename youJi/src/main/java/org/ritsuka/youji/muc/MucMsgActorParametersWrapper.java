package org.ritsuka.youji.muc;

import org.jivesoftware.smack.packet.Packet;

/**
 * Date: 10/2/11
 * Time: 5:44 PM
 */
public final class MucMsgActorParametersWrapper {
    private final Packet packet;

    public MucMsgActorParametersWrapper(final Packet packet) {
        this.packet = packet;
    }

    public Packet getMessage() {
        return packet;
    }
}
