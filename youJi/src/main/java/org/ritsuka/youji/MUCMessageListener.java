package org.ritsuka.youji;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smack.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 9/29/11
 * Time: 9:32 PM
 */
public class MUCMessageListener implements PacketListener {
    private XMPPWorker worker;
    private MultiUserChat chat;

    private Logger log()
    {
        return LoggerFactory.getLogger(toString());
    }

    MUCMessageListener(XMPPWorker a_worker, MultiUserChat a_chat) {
        worker = a_worker;
        chat = a_chat;
    }

    public String toString() {
        return worker.connection().getUser() +
               ":" + chat.getRoom() + "/" + chat.getNickname();
    }

    @Override
    public void processPacket(Packet packet) {
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
