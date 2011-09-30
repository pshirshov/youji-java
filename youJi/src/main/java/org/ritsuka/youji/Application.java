package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.actorOf;

public class Application {
    private Logger log()
    {
        return LoggerFactory.getLogger(Application.class);
    }

    public static void main(final String[] args1) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ActorRef master = actorOf(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Supervisor(latch);
            }
        }).start();

        master.tell(new AccountData("youji_sagan"));
        master.tell(new AccountData("youji_sagan1"));
        latch.await();
    }
}
