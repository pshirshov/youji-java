package org.ritsuka.youji.pm;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * Date: 10/2/11
 * Time: 5:25 PM
 */
public interface IPmHandler {
    void handlePm(ActorRef worker, Chat chat,   Message message);
}
