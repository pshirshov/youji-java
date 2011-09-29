package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.*;

public class Application  {
    public static void main(final String[] args1) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        //master.tell(new Calculate());
     /*   System.out.println("Hi");
        ActorRef myActor = actorOf(XMPPWorker.class);
        myActor.start();
        myActor.tell(new AccountData());
        System.out.println("Bye");*/
        ActorRef master = actorOf(new UntypedActorFactory() {
      public UntypedActor create() {
        return new Supervisor(latch);
      }
    }).start();
        master.tell(new AccountData());
        latch.await();
    }
}
