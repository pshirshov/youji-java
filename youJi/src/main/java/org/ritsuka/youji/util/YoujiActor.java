package org.ritsuka.youji.util;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Date: 10/4/11
 * Time: 10:39 PM
 */
public abstract class YoujiActor extends UntypedActor {
    protected ActorRef selfRef() {
        return (ActorRef)self();
    }
}
