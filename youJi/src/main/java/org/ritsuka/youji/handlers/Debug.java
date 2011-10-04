package org.ritsuka.youji.handlers;

import akka.actor.ActorRef;
import akka.actor.Actors;
import com.google.gson.Gson;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.natsuo.Log;
import org.ritsuka.youji.muc.IMucMsgHandler;
import org.ritsuka.youji.pm.IPmHandler;
import org.ritsuka.youji.util.CommandParser;
import org.ritsuka.youji.util.HTTPGet;
import org.ritsuka.youji.util.XMPPUtil;
import org.slf4j.LoggerFactory;

import java.util.List;

//import org.apache.commons.lang.StringUtils;

/**
 * Date: 04.10.11
 * Time: 16:30
 */
public class Debug implements IPmHandler, IMucMsgHandler{

    private ActorRef worker;
    private MultiUserChat muc;

    private Log log() {
        return new Log(LoggerFactory.getLogger(Debug.class));
    }

    @Override
    public IMucMsgHandler setContext(ActorRef worker, MultiUserChat chat) {
        this.worker = worker;
        this.muc = chat;
        return this;
    }

    @Override
    public void handleMucMsg(Message message) {
        if (!XMPPUtil.isUsualMessage(message))
            return;

        if (XMPPUtil.isYoujiMessage(message, muc))
            return;
        Message reply = process(message.getBody(), message);

        if (null == reply)
            return;

        try {
            reply.setType(Message.Type.groupchat);
            reply.setTo(muc.getRoom());
            muc.sendMessage(reply);
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public IPmHandler setContext(ActorRef worker) {
        this.worker = worker;
        return this;
    }

    @Override
    public void handlePm(Chat chat, Message message) {
        Message reply = process(message.getBody(), message);
        if (null == reply)
            return;
        try {
            chat.sendMessage(reply);
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Message process(final String rawQuery, Message sourceMessage) {
        String query = rawQuery;
        CommandParser.ParsedCommand cmd = new CommandParser(rawQuery).parse();
        if (null == cmd)
            return null;

        if (!cmd.is("dbg"))
            return null;

        Message reply = new Message();
        StringBuilder sb = new StringBuilder();
        List<String> args = cmd.getArgs();
        if (args.get(0).equals("actors"))
        {
            ActorRef[] actors = Actors.registry().actors();
            sb.append("Here is ").append(actors.length).append(" actors");
            if (args.size() > 1) {
                sb.append(":\n");
                if (args.get(1).equals("detail")) {
                    for (ActorRef actor:actors) {
                        sb.append(actor.getUuid()).append(": ").append(actor.getId()).append('\n');
                    }
                }
            }
        }
        else if(args.get(0).equals("rungc"))
        {
            sb.append("System.gc() called");
            System.gc();
        }

        reply.setBody(sb.toString());
        return reply;
    }

}
