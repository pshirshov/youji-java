package org.ritsuka.youji.pm;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
public final class PMActor extends UntypedActor {
    private final IPmHandler handler;

    public static UntypedActorFactory create(final IPmHandler handler) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new PMActor(handler);
            }
        };
    }

    private PMActor(final IPmHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof PMActorParametersWrapper){
            PMActorParametersWrapper pm = (PMActorParametersWrapper) message;
             handler.handlePm(pm.getActor(), pm.getChat(), pm.getMessage());
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
}
