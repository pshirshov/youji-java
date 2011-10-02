package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.event.ReconnectedEvent;
import org.ritsuka.youji.event.RunXmppWorkerEvent;
import org.ritsuka.youji.muc.*;
import org.ritsuka.youji.muc.event.ForcedMUCLeaveEvent;
import org.ritsuka.youji.muc.event.MUCJoinErrorProcessor;
import org.ritsuka.youji.muc.threaded.MUCMessageListenerThreaded;
import org.ritsuka.youji.muc.threaded.MUCUserStatusListenerThreaded;
import org.ritsuka.youji.pm.ChatListenerThreaded;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static akka.actor.Actors.actorOf;

/**
 * Date: 9/29/11
 * Time: 7:51 PM
 */
public class XMPPWorker extends UntypedActor {
    private Log log() {
        return new Log(LoggerFactory.getLogger(toString()));
    }

    private Connection connection = null;
    AccountData account;
    HashMap<String, ConferenceState> conferences = new HashMap<String, ConferenceState>();

    public Connection connection() {
        return connection;
    }
    static public UntypedActorFactory create(final AccountData account) {
        return new UntypedActorFactory() {
            public UntypedActor create() {
                return new XMPPWorker(account);
            }
        };
    }
    private XMPPWorker(AccountData accountData){
        this.account = accountData;
    }

    private void init() {
        log().debug("Worker initiated: {}", account);
        try {
            logon();
        } catch (XMPPException e) {
            log().debug("Logon error: {}, {}", account, e);
        }
        onLoggedIn();
    }

    private void logon() throws XMPPException {
        if (null == connection) {
            log().debug("Logging in using {}", account);
            connection = new XMPPConnection(account.server());
            connection.connect();
            connection.login(account.login(), account.password(), account.resource());
            connection.addConnectionListener(new YConnectionListener(selfRef(), objId()));
            ChatManager chatmanager = connection.getChatManager();
            chatmanager.addChatListener(new ChatListenerThreaded(selfRef()));
        }
    }

    private void joinConferences(AccountData account) {
        log().debug("Joining conferences...");
        List<ConferenceData> conferences = account.conferences();
        for (ConferenceData conf : conferences) {
            joinConf(conf);
        }
    }

    private void joinConf(ConferenceData conf) {
        log().debug("Joining to conference: {}", conf);
        if (!connection.isConnected())
            log().error("Connection inactive: {}", conf);

        String roomJid = conf.roomJid();
        ConferenceState state = conferences.get(roomJid);
        if (null == state) {
            MultiUserChat muc = new MultiUserChat(connection, roomJid);
            muc.addMessageListener(new MUCMessageListenerThreaded(selfRef(), muc));
            muc.addUserStatusListener(new MUCUserStatusListenerThreaded(selfRef(), muc));
            state = new ConferenceState(conf, muc);
            conferences.put(roomJid, state);
        }

        MultiUserChat muc = state.muc();
        boolean success = false;
        try {
            state.newAttempt();
            if (conf.password() != null)
                muc.join(state.nick(), conf.password());
            else
                muc.join(state.nick());
            state.success();
        } catch (XMPPException e) {
            log().debug(e.toString());
            XMPPError error = e.getXMPPError();
            if (null != error) {
                (new MUCJoinErrorProcessor(log(), selfRef())).processMucError(state, error);
            }
        }
    }

    private ActorRef selfRef() {
        return (ActorRef)self();
    }


    private void onLoggedIn() {
        joinConferences(account);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RunXmppWorkerEvent){
             init();
        }
        /*else if (message instanceof TestMucHandler)
        {
            TestMucHandler listenerAk = (TestMucHandler)message;
            listenerAk.setWorker(this);
            listenerAk.processPacketInActor();
        }*/
        else if (message instanceof ReconnectedEvent)
        {
            onLoggedIn();
        }
        else if (message instanceof ForcedMUCLeaveEvent)
        {
            ForcedMUCLeaveEvent event = (ForcedMUCLeaveEvent)message;
            String room = event.getChat().getRoom();
            String kicker = event.getKicker();
            String reason = event.getReason();
            String descr = event.action();
            log().debug("{}. Room {} by {} with reason {}", descr, room, kicker, reason);
            ConferenceState conf = conferences.get(event.getChat().getRoom());
            assert null != conf;
            joinConf(conf.conferenceData());
        }
        else if (message instanceof ConferenceData) {
            if ((null != connection) && connection.isConnected()) {
                ConferenceData conf = (ConferenceData) message;
                joinConf(conf);
            }
        } else
            throw new IllegalArgumentException("Unknown message, state=connected [" + message + "]");
    }

    @Override
    public void postStop() {
        log().debug("Worker ended");
        connection.disconnect();
    }

    public String objId() {
        return Integer.toHexString(((Object)this).hashCode());
    }
    public String toString() {
        String connectionId = "offline";
        String user = null;
        if (null != connection)
        {
            if (connection.isConnected())
            {
                connectionId = connection.getConnectionID();
            }
            user = connection.getUser();

        }
        return String.format("XW.%s.%s.%s", objId(), connectionId, user);
    }
}
