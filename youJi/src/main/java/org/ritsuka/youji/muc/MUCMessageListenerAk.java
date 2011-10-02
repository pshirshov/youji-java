package org.ritsuka.youji.muc;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.XMPPWorker;
import org.ritsuka.youji.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MUCMessageListenerAk {
    private Packet packet;
    private XMPPWorker worker;
    private MultiUserChat chat;

    private Log log() {
        return new Log(LoggerFactory.getLogger(toString()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MM.");
        sb.append(worker.objId());
        sb.append(".");
        sb.append(chat.getRoom());
        sb.append("/");
        sb.append(chat.getNickname());
        return sb.toString();
    }

    public void processPacketInActor()
    {
        Message message = (Message)packet;
        String from = message.getFrom();
        String nick = StringUtils.parseResource(from);
        //String confName = StringUtils.parseName(from);
        String jid = StringUtils.parseBareAddress(from);

        String body = message.getBody();
        log().debug("{} in {}: {}", nick, jid, body);

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

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public XMPPWorker getWorker() {
        return worker;
    }

    public void setWorker(XMPPWorker worker) {
        this.worker = worker;
    }

    public MultiUserChat getChat() {
        return chat;
    }

    public void setChat(MultiUserChat chat) {
        this.chat = chat;
    }
}
