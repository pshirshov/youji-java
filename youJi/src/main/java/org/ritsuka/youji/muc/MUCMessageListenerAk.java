package org.ritsuka.youji.muc;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.XMPPWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MUCMessageListenerAk {
    public Packet packet;
    public XMPPWorker worker;
    public MultiUserChat chat;

    private Logger log()
    {
        return LoggerFactory.getLogger(toString());
    }

    public String toString() {
        return String.format("%s:%s/%s"
                , worker.connection().getUser()
                , chat.getRoom()
                , chat.getNickname());
    }

    public void processPacketInActor()
    {
        Message message = (Message)packet;
        String from = message.getFrom();
        String nick = StringUtils.parseResource(from);
        //String confName = StringUtils.parseName(from);
        String jid = StringUtils.parseBareAddress(from);

        log().debug(String.format("%s in %s: %s", nick, jid, message.getBody()));

        String body = message.getBody();
        try {
            if (body.length() < 10)
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
