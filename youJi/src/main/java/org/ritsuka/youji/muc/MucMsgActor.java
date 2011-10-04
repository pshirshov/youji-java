package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import org.ritsuka.natsuo.Log;
import org.ritsuka.youji.util.YoujiActor;
import org.slf4j.LoggerFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
final class MucMsgActor extends YoujiActor {
    private final IMucMsgHandler handler;
    private Log log() {
        return new Log(LoggerFactory.getLogger(MucMsgActor.class));
    }

    public static UntypedActorFactory create(final IMucMsgHandler handler) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new MucMsgActor(handler);
            }
        };
    }

    private MucMsgActor(final IMucMsgHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof MucMsgActorParametersWrapper){
            MucMsgActorParametersWrapper pm = (MucMsgActorParametersWrapper) message;
            try {
                handler.handleMucMsg(pm.getMessage());
            } finally {
                selfRef().stop();
            }
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
    @Override
    public void postStop() {
    }
}
