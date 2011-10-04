package org.ritsuka.youji.handlers;

import akka.actor.ActorRef;
import com.google.gson.Gson;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.muc.IMucMsgHandler;
import org.ritsuka.youji.pm.IPmHandler;
import org.ritsuka.youji.util.CommandParser;
import org.ritsuka.youji.util.HTTPGet;

import java.util.List;

/**
 * Date: 04.10.11
 * Time: 16:30
 */
public class GoogleIt implements IPmHandler, IMucMsgHandler{

    private ActorRef worker;
    private MultiUserChat muc;

    private final class SearchResult {
        private String titleNoFormatting;
        private String content;
        private String unescapedUrl;

        public String getTitleNoFormatting() {
            return titleNoFormatting;
        }

        public void setTitleNoFormatting(String titleNoFormatting) {
            this.titleNoFormatting = titleNoFormatting;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUnescapedUrl() {
            return unescapedUrl;
        }

        public void setUnescapedUrl(String unescapedUrl) {
            this.unescapedUrl = unescapedUrl;
        }
    }

    private final class ResponseData {
        private List<SearchResult> results;

        public List<SearchResult> getResults() {
            return results;
        }

        public void setResults(List<SearchResult> results) {
            this.results = results;
        }
    }

    private final class GoogleSearchResult {
        private ResponseData responseData;
        private Integer responseStatus;

        public ResponseData getResponseData() {
            return responseData;
        }

        public void setResponseData(ResponseData responseData) {
            this.responseData = responseData;
        }

        public Integer getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(Integer responseStatus) {
            this.responseStatus = responseStatus;
        }
    }

    @Override
    public IMucMsgHandler setContext(ActorRef worker, MultiUserChat chat) {
        this.worker = worker;
        this.muc = chat;
        return this;
    }

    @Override
    public void handleMucMsg(Packet message) {
        if (null != message.getExtension("urn:xmpp:delay"))
            return;

        Message reply = googleIt(((Message)message).getBody());
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
        Message reply = googleIt(message.getBody());
        if (null == reply)
            return;
        try {
            chat.sendMessage(reply);
        } catch (XMPPException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Message googleIt(final String rawQuery) {
        String query = rawQuery;
        CommandParser.ParsedCommand cmd = new CommandParser(rawQuery).parse();
        if (null == cmd)
            return null;

        if (cmd.is("g") || cmd.is("г")) {
            query = cmd.getRawArg();
            Message reply = new Message();

            query = HTTPGet.urlencode(query);
            String urlToLoad = String.format("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=%s&hl=ru", query);
            try {
                String output = HTTPGet.getHTML(urlToLoad);
                GoogleSearchResult result = (new Gson()).fromJson(output, GoogleSearchResult.class);
                StringBuilder sb = new StringBuilder();
                ResponseData response = result.getResponseData();
                if (null != response) {
                    List<SearchResult> results = response.getResults();
                    if (null != results) {
                        for (SearchResult link: results) {
                            sb.append(link.getTitleNoFormatting());
                            sb.append('\n');
                            sb.append(":: ");
                            sb.append(link.getUnescapedUrl());
                            sb.append('\n');
                            sb.append(":: ");
                            sb.append(link.getContent());
                            sb.append("\n\n");
                        }
                        reply.setBody(sb.toString());
                    }
                    else
                        reply.setBody("Nothing found");
                } else {
                    reply.setBody("Can't parse google's reply: " + output);
                }

            } catch (Throwable e) {
                reply.setBody(e.toString());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return reply;
        }
        return null;
    }
}
