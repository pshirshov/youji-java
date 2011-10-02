package org.ritsuka.youji.pm;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

/**
 * Date: 9/30/11
 * Time: 9:30 PM
 */
public final class ChatListenerThreaded implements ChatManagerListener {
    private ActorRef worker;

    public ChatListenerThreaded(final ActorRef worker) {
        this.worker = worker;
    }

    @Override
    public void chatCreated(final Chat chat, final boolean createdLocally) {
        //if (!createdLocally)
        chat.addMessageListener(new MessageListenerThreaded(worker));
    }
}
