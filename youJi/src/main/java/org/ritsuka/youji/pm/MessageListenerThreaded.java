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
    private final List<IPmHandler> handlers = new ArrayList<IPmHandler>();

    public MessageListenerThreaded(final ActorRef worker) {
        this.worker = worker;

        // TODO: add appropriate plugins
        handlers.add(new TestPmHandler());
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
        for (IPmHandler handler:handlers) {
            ActorRef actor = actorOf(PmActor.create(handler)).start();
            actor.tell(new PmActorParametersWrapper(worker, chat, message));
        }
    }
}
