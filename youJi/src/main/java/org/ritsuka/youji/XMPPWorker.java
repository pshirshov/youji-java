package org.ritsuka.youji;

import akka.actor.ActorRef;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Date: 9/29/11
 * Time: 7:51 PM
 */
public class XMPPWorker extends UntypedActor {
    private Logger log()
    {
        return LoggerFactory.getLogger(toString());
    }

    private Connection connection = null;
    AccountData account;
    HashMap<String, ConferenceState> conferences = new HashMap<String, ConferenceState>();

    public Connection connection() {
        return connection;
    }
    private void logon() throws XMPPException {
        if (null == connection) {
            log().debug("Logging in");
            connection = new XMPPConnection(account.server());
            connection.connect();
            connection.login(account.login(), account.password(), account.resource());
            connection.addConnectionListener(new YConnectionListener(this));
            /*connection.addPacketListener(new PacketListener() {
                @Override
                public void processPacket(Packet packet) {
                    log().debug("Packet: " + packet.toXML());
                }
            }, new PacketFilter() {
                @Override
                public boolean accept(Packet packet) {
                    return true;
                }
            });*/
        }
    }

    private void joinConferences(AccountData account) {
        List<ConferenceData> conferences = account.conferences();
        for (ConferenceData conf : conferences) {
            joinConf(conf);
        }
    }

    private void joinConf(ConferenceData conf) {
        if (!connection.isConnected())
            throw new IllegalArgumentException("Connection inactive [" + conf + "]");

        String roomJid = conf.roomJid();
        ConferenceState state = conferences.get(roomJid);
        if (null == state) {
            MultiUserChat muc = new MultiUserChat(connection, roomJid);
            muc.addMessageListener(new MUCMessageListener(this, muc));
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
                if (error.getCode() == 407) {
                    Integer pauseBeforeNextAttempt = state.pauseBeforeNextAttempt();
                    log().debug("Pause: " + pauseBeforeNextAttempt);
                    Scheduler.scheduleOnce((ActorRef) this.self(), conf, pauseBeforeNextAttempt, TimeUnit.MILLISECONDS);
                } else if (error.getCode() == 409) {
                    Integer pauseBeforeNextAttempt = state.pauseBeforeNextAttempt();
                    log().debug("Pause: " + pauseBeforeNextAttempt);
                    state.nickConflict();
                    Scheduler.scheduleOnce((ActorRef) this.self(), conf, pauseBeforeNextAttempt, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    public void onLoggedIn() {
        joinConferences(account);
        log().debug(this + ":after logon: " + account.toString());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        /*if ((null != connection) && !connection.isConnected())
        {
            conferences.clear();
            connection = null;
        }*/
        if (message instanceof AccountData) {
            log().debug(this + ":worker message: " + message.toString());
            account = (AccountData) message;
            logon();
            onLoggedIn();

            /*
            getContext().replyUnsafe(new Result(result));*/
        } else if (message instanceof ConferenceData) {
            if ((null != connection) && connection.isConnected()) {
                ConferenceData conf = (ConferenceData) message;
                log().debug(this + ":worker rejoin: " + message.toString());
                joinConf(conf);
            }
        } else
            throw new IllegalArgumentException("Unknown message, state=connected [" + message + "]");
    }

    @Override
    public void postStop() {
        log().debug(this + ":worker ended");
    }

    public String toString() {
        String connected = "offline";
        String user = null;
        if (null != connection)
        {
            if (connection().isConnected())
            {
                connected = connection().getConnectionID();
            }
            user = connection.getUser();

        }
        return String.format("XMPP%s:%s:%s", Integer.toHexString(1), user, connected);
    }
}
