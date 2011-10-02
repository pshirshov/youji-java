package org.ritsuka.youji;

// TODO: shared JIDs list
// TODO: shared config
// TODO: reply actors+some kind of plugins
// TODO: history in mongodb
// TODO: blogging
// TODO: ant+maven buildscripts
// TODO: utils->separate lib

import akka.actor.ActorRef;
import akka.actor.Actors;
import org.ritsuka.youji.util.Log;
import org.ritsuka.youji.util.yaconfig.YaConfig;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static akka.actor.Actors.actorOf;

public final class Application {
    private Application() {
    }

    private static Log log() {
        return new Log(LoggerFactory.getLogger("APP"));
    }

    public static void main(final String[] args1) throws InterruptedException {
        YaConfig.verbose = true;
        Constructor constructor = new Constructor();
        constructor.addTypeDescription(new TypeDescription(URI.class, "!uri"));
        YaConfig.load(constructor);

        final CountDownLatch latch = new CountDownLatch(1);
        ShutdownHook shutdownHook = new ShutdownHook(latch);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        URI url = YaConfig.get(Config.DB_LOGS);
        System.out.println(url);

        ActorRef master = actorOf(Supervisor.create(latch)).start();

        List<String> initialAccounts = YaConfig.get(Config.INITIAL_ACCOUNTS);
        for (String account:initialAccounts)
        {
            master.tell(new AccountData(account));
        }

        latch.await();
        log().info("Exiting...");
        Actors.registry().shutdownAll();
    }
}
