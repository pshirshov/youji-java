package org.ritsuka.youji.handlers;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.ritsuka.natsuo.Log;
import org.ritsuka.youji.pm.IPmHandler;
import org.slf4j.LoggerFactory;

/**
 * Date: 10/2/11
 * Time: 5:37 PM
 */
public final class TestPmHandler implements IPmHandler {
    private ActorRef worker;

    private Log log(final String id) {
        return new Log(LoggerFactory.getLogger(id));
    }

    @Override
    public IPmHandler setContext(ActorRef worker) {
        this.worker = worker;
        return this;
    }

    @Override
    public void handlePm(final Chat chat, Message message) {
        System.out.println(hashCode());
        Log log = log(chat.getParticipant());
        log.debug("PM: {}", message.toXML());
        Message newMessage = new Message();
        newMessage.setBody("Wtf?");
        //message.setProperty("favoriteColor", "red");
        try {
            chat.sendMessage(newMessage);
        } catch (XMPPException e) {
            log.error("Can't send: {}: {}", message.toXML(), e);
        }
    }
}
