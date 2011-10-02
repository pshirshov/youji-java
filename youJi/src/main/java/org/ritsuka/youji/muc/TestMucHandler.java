package org.ritsuka.youji.muc;

import akka.actor.ActorRef;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.util.Log;
import org.slf4j.LoggerFactory;

public final class TestMucHandler implements IMucMsgHandler {
    //private XMPPWorker worker;
    private MultiUserChat chat;

    private Log log() {
        return new Log(LoggerFactory.getLogger(toString()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MM.");
        //sb.append(worker.objId());
        sb.append(".");
        sb.append(chat.getRoom());
        sb.append("/");
        sb.append(chat.getNickname());
        return sb.toString();
    }

    @Override
    public void handleMucMsg(final ActorRef worker,
                             final MultiUserChat chat,
                             final Packet packet) {
        //this.worker = worker;
        this.chat = chat;
        Message message = (Message)packet;

        String from = message.getFrom();
        String nick = StringUtils.parseResource(from);
        //String confName = StringUtils.parseName(from);
        String jid = StringUtils.parseBareAddress(from);

        String body = message.getBody();
        log().debug("{}", body);
/*        try {
            if (body.length() < 10)
            {
                String answer = String.format("Ходят тут всякие илитарии навроде %s: блядь, пишут хуиту навроде '%s'",
                        nick, body);
                chat.sendMessage(answer);
            }

        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/

    }
}
