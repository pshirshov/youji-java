package org.ritsuka.youji.pm;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
public class PMActor extends UntypedActor {
    private final IPmHandler handler;

    static public UntypedActorFactory create(final IPmHandler handler) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new PMActor(handler);
            }
        };
    }

    private PMActor(IPmHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PMActorParametersWrapper){
            PMActorParametersWrapper pm = (PMActorParametersWrapper) message;
             handler.handlePm(pm.getActor(), pm.getChat(), pm.getMessage());
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
}
