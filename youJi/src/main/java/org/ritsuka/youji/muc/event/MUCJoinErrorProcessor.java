package org.ritsuka.youji.muc.event;

import akka.actor.ActorRef;
import akka.actor.Scheduler;
import org.jivesoftware.smack.packet.XMPPError;
import org.ritsuka.youji.muc.MucData;
import org.ritsuka.youji.muc.MucState;
import org.ritsuka.youji.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Date: 9/30/11
 * Time: 9:58 PM
 */
public final class MUCJoinErrorProcessor {
    private final Log log;
    private final ActorRef actor;

    public MUCJoinErrorProcessor(final Log log, final ActorRef actor) {
        this.log = log;
        this.actor = actor;
    }

    public void processMucError(final MucState state,
                                final XMPPError error) {
        MucData conf = state.conferenceData();

        Integer code = error.getCode();
        switch (code)
        {
            case 407:
            case 409:
            case 403: {
                Integer pauseBeforeNextAttempt = state.pauseBeforeNextAttempt();
                log.debug("Pause: {}", pauseBeforeNextAttempt);
                if (409 == code)
                    state.nickConflict();
                Scheduler.scheduleOnce(actor, conf, pauseBeforeNextAttempt, TimeUnit.MILLISECONDS);
                break;
            }
            default: {
                log.debug("Unknown code {}, not rejoining", code);
                break;
            }
        }
    }
}
