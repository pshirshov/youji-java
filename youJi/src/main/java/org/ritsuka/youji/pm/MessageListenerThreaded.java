package org.ritsuka.youji.pm;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

/**
 * Date: 9/30/11
 * Time: 9:32 PM
 */
public class MessageListenerThreaded implements MessageListener{
    private ActorRef worker;

    public MessageListenerThreaded(ActorRef worker) {
        this.worker = worker;
    }

    private Log log(String id) {
        return new Log(LoggerFactory.getLogger(id));
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Log log = log(chat.getParticipant());
        String body = message.getBody();
        if (Message.Type.error == message.getType())
        {
            log.warn("Ignored Err PM: E:{} MSG:{} ({})", message.getError().getMessage(), body, message.toXML());
            return;
        }
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
