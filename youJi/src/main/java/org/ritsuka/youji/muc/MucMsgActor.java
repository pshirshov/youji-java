package org.ritsuka.youji.muc;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
public class MucMsgActor extends UntypedActor {
    private final IMucMsgHandler handler;

    static public UntypedActorFactory create(final IMucMsgHandler handler) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new MucMsgActor(handler);
            }
        };
    }

    private MucMsgActor(IMucMsgHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MucMsgActorParametersWrapper){
            MucMsgActorParametersWrapper pm = (MucMsgActorParametersWrapper) message;
             handler.handleMucMsg(pm.getActor(), pm.getChat(), pm.getMessage());
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
}
