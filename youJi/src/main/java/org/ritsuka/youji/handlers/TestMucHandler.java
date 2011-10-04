package org.ritsuka.youji.handlers;

import akka.actor.ActorRef;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.natsuo.Log;
import org.ritsuka.youji.muc.IMucMsgHandler;
import org.slf4j.LoggerFactory;

public final class TestMucHandler implements IMucMsgHandler {
    private ActorRef worker;
    private MultiUserChat chat;

    private Log log() {
        return new Log(LoggerFactory.getLogger(toString()));
    }

    @Override
    public IMucMsgHandler setContext(final ActorRef worker, final MultiUserChat chat) {
        this.worker = worker;
        this.chat = chat;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MM.");
        sb.append(worker.getUuid().toString());
        sb.append(".");
        sb.append(chat.getRoom());
        sb.append("/");
        sb.append(chat.getNickname());
        return sb.toString();
    }

    @Override
    public void handleMucMsg(final Packet packet) {
        Message message = (Message)packet;

        String from = message.getFrom();
        String nick = StringUtils.parseResource(from);
        //String confName = StringUtils.parseName(from);
        String jid = StringUtils.parseBareAddress(from);

        String body = message.getBody();
        log().debug("{}: {}", nick, body);
        try {
            if (body.contains("тест") && (!body.contains("илитарии")))
            {
                String answer = String.format("Ходят тут всякие илитарии навроде %s: блядь, пишут хуиту навроде '%s'",
                        nick, body);
                chat.sendMessage(answer);
            }

        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
