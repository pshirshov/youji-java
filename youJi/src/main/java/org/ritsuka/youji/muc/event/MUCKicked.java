package org.ritsuka.youji.muc.event;

import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 9/30/11
 * Time: 9:37 PM
 */
public final class MUCKicked implements ForcedMUCLeaveEvent {
    private MultiUserChat chat;
    private String reason;
    private String kicker;

    public MUCKicked(final MultiUserChat chat, final String reason,
                     final String kicker) {
        this.reason = reason;
        this.chat = chat;
        this.kicker = kicker;
    }

    @Override
    public MultiUserChat getChat() {
        return chat;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getKicker() {
        return kicker;
    }

    @Override
    public String action()
    {
        return "KICK";
    }
}
