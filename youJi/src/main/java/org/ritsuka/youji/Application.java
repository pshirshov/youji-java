package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import org.ritsuka.youji.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.actorOf;

public class Application {
    static private Log log() {
        return new Log(LoggerFactory.getLogger("APP"));
    }
    public static void main(final String[] args1) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ShutdownHook shutdownHook = new ShutdownHook(latch);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        ActorRef master = actorOf(Supervisor.create(latch)).start();

        master.tell(new AccountData("youji_sagan"));
        master.tell(new AccountData("youji_sagan1"));
        latch.await();
        log().info("Exiting...");
        Actors.registry().shutdownAll();
    }
}
