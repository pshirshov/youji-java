package org.ritsuka.youji.util;


import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 10/4/11
 * Time: 9:09 PM
 */
public class XMPPUtil {
    public static boolean isUsualMessage(Message message){
        Message.Type mtype = message.getType();

        boolean isGroupchat = (mtype == Message.Type.groupchat);
        boolean isChat = (mtype == Message.Type.chat);
        boolean isHistory = (null != message.getExtension("urn:xmpp:delay"));
        boolean isSubject = (null != message.getSubject());

        return (isChat || isGroupchat) && !(isHistory || isSubject);
    }

    public static boolean isYoujiMessage(Message message, MultiUserChat muc) {
        String from = message.getFrom();
        String idInMuc = muc.getRoom() + "/" + muc.getNickname();

        if (from.equals(idInMuc))
            return true;

        //  TODO: other IDs
        return false;
    }
}
