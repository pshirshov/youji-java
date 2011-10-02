package org.ritsuka.youji.pm;

import akka.actor.ActorRef;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static akka.actor.Actors.actorOf;

/**
 * Date: 9/30/11
 * Time: 9:32 PM
 */
public class MessageListenerThreaded implements MessageListener{
    private ActorRef worker;
    private List<IPmHandler> handlers;

    public MessageListenerThreaded(ActorRef worker) {
        this.worker = worker;

        // TODO: add appropriate plugins
        handlers = new ArrayList<IPmHandler>();
        handlers.add(new TestHandler());
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
        for (IPmHandler handler:handlers) {
            ActorRef actor = actorOf(PMActor.create(handler)).start();
            actor.tell(new PMActorParametersWrapper(worker, chat, message));
        }
    }
}
