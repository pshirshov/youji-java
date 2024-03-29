package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.UserStatusListener;
import org.ritsuka.youji.muc.event.MUCBanned;
import org.ritsuka.youji.muc.event.MUCKicked;
import org.ritsuka.youji.muc.event.MUCVoiceRevoked;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public final class MucUserStatusListenerThreaded
        implements UserStatusListener {
    private final ActorRef worker;
    private final MultiUserChat chat;

    public MucUserStatusListenerThreaded(final ActorRef a_worker,
                                         final MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    @Override
    public void kicked(final String actor, final String reason) {
        worker.tell(new MUCKicked(chat, reason, actor));
    }

    @Override
    public void voiceRevoked() {
        worker.tell(new MUCVoiceRevoked(chat));
    }

    @Override
    public void banned(final String actor, final String reason) {
        worker.tell(new MUCBanned(chat, reason, actor));
    }


    @Override
    public void voiceGranted() {
    }

    @Override
    public void membershipGranted() {
    }

    @Override
    public void membershipRevoked() {
    }

    @Override
    public void moderatorGranted() {
    }

    @Override
    public void moderatorRevoked() {
    }

    @Override
    public void ownershipGranted() {
    }

    @Override
    public void ownershipRevoked() {
    }

    @Override
    public void adminGranted() {
    }

    @Override
    public void adminRevoked() {
    }
}
