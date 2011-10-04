package org.ritsuka.youji.pm;

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
public final class PmActor extends YoujiActor {
    private Log log() {
        return new Log(LoggerFactory.getLogger(PmActor.class));
    }
    private final IPmHandler handler;

    public static UntypedActorFactory create(final IPmHandler handler) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new PmActor(handler);
            }
        };
    }

    private PmActor(final IPmHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof PmActorParametersWrapper){
            PmActorParametersWrapper pm = (PmActorParametersWrapper) message;
            try {
                handler.handlePm(pm.getChat(), pm.getMessage());
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
