package org.ritsuka.youji.muc.threaded;

import akka.actor.ActorRef;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.UserStatusListener;
import org.ritsuka.youji.muc.event.MUCVoiceRevoked;
import org.ritsuka.youji.muc.event.MUCBanned;
import org.ritsuka.youji.muc.event.MUCKicked;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCUserStatusListenerThreaded implements UserStatusListener {
    private ActorRef worker;
    private MultiUserChat chat;

    public MUCUserStatusListenerThreaded(ActorRef a_worker, MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    @Override
    public void kicked(String actor, String reason) {
        worker.tell(new MUCKicked(chat, reason, actor));
    }

    @Override
    public void voiceRevoked() {
        worker.tell(new MUCVoiceRevoked(chat));
    }

    @Override
    public void banned(String actor, String reason) {
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
