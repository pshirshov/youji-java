package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.actorOf;

/**
 * Date: 9/29/11
 * Time: 8:48 PM
 */
public class Supervisor extends UntypedActor {
    //private ActorRef router;
    private final CountDownLatch latch;

    /*static class XMPPRouter extends UntypedLoadBalancer {
        private final InfiniteIterator<ActorRef> workers;

        public XMPPRouter(ActorRef[] workers) {
            this.workers = new CyclicIterator<ActorRef>(asList(workers));
        }

        public InfiniteIterator<ActorRef> seq() {
            return workers;
        }
    }*/

    ArrayList<ActorRef> workers = new ArrayList<ActorRef>();

    public Supervisor(CountDownLatch latch) {
        System.out.println("supervisor constructor");
        this.latch = latch;


        // create the workers
        /*final ActorRef[] workers = new ActorRef[1];
       for (int i = 0; i < workers.length; ++i) {
         workers[i] = ;
       } */


/*      // wrap them with a load-balancing router
      router = actorOf(new UntypedActorFactory() {
        public UntypedActor create() {
          return new XMPPRouter(workers);
        }
      }).start();*/
    }

    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println(toString() + ":message: " + message.toString());
        if (message instanceof AccountData) {
            ActorRef worker = actorOf(XMPPWorker.class).start();
            workers.add(worker);
            worker.tell(message, getContext());
        } else
            throw new IllegalArgumentException("Unknown message [" + message + "]");
    }

    @Override
    public void postStop() {
        System.out.println("supervisor ended");
        latch.countDown();
    }
}
