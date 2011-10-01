package org.ritsuka.youji;

// TODO: shared JIDs list
// TODO: shared config
// TODO: reply actors+some kind of plugins
// TODO: history in mongodb
// TODO: blogging

import akka.actor.ActorRef;
import akka.actor.Actors;
import org.ritsuka.youji.util.Log;
import org.ritsuka.youji.util.yaconfig.YaConfig;
import org.slf4j.LoggerFactory;
import sun.security.krb5.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.actorOf;

public class Application {
    static private Log log() {
        return new Log(LoggerFactory.getLogger("APP"));
    }
    public static void main(final String[] args1) throws InterruptedException {
        YaConfig.loadConfig();

        final CountDownLatch latch = new CountDownLatch(1);
        ShutdownHook shutdownHook = new ShutdownHook(latch);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        ActorRef master = actorOf(Supervisor.create(latch)).start();

        List initialAccounts = YaConfig.get(Config.INITIAL_ACCOUNTS);
        for (Object obj:initialAccounts)
        {
            String account = obj.toString();
            master.tell(new AccountData(account));
        }

        latch.await();
        log().info("Exiting...");
        Actors.registry().shutdownAll();
    }
}
