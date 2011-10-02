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
public final class MessageListenerThreaded implements MessageListener{
    private final ActorRef worker;

    public MessageListenerThreaded(final ActorRef worker) {
        this.worker = worker;
    }

    private List<IPmHandler> instantiateHandlers() {
        // TODO: add appropriate plugins
        // TODO: is it safe to create only one list per listener? (see muc listener)
        List<IPmHandler> handlers = new ArrayList<IPmHandler>();
        handlers.add(new TestPmHandler().setContext(worker));
        return handlers;
    }

    private Log log(final String id) {
        return new Log(LoggerFactory.getLogger(id));
    }

    @Override
    public void processMessage(final Chat chat, final Message message) {
        Log log = log(chat.getParticipant());
        String body = message.getBody();
        if (Message.Type.error == message.getType())
        {
            log.warn("Ignored Err PM: E:{} MSG:{} ({})", message.getError().getMessage(), body, message.toXML());
            return;
        }
        List<IPmHandler> handlers = instantiateHandlers();

        for (IPmHandler handler:handlers) {
            ActorRef actor = actorOf(PmActor.create(handler)).start();
            actor.tell(new PmActorParametersWrapper(chat, message));
        }
    }
}
