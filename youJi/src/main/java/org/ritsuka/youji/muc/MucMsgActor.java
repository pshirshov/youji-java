package org.ritsuka.youji.muc;

import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import org.ritsuka.natsuo.Log;
import org.slf4j.LoggerFactory;

/**
 * Date: 10/2/11
 * Time: 5:41 PM
 */
final class MucMsgActor extends UntypedActor {
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
             handler.handleMucMsg(pm.getMessage());
        }
        else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }
    @Override
    public void postStop() {
        log().debug("MucActor ended");
    }
}
