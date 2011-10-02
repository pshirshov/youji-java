package org.ritsuka.youji.muc.event;

import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 9/30/11
 * Time: 9:40 PM
 */
public final class MUCVoiceRevoked {
    private final MultiUserChat chat;

    public MUCVoiceRevoked(final MultiUserChat chat) {
        this.chat = chat;
    }

    public MultiUserChat getChat() {
        return chat;
    }
}
