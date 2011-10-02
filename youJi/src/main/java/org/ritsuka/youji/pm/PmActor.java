package org.ritsuka.youji.pm;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
public final class PmActor extends UntypedActor {
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
             handler.handlePm(pm.getChat(), pm.getMessage());
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
}
