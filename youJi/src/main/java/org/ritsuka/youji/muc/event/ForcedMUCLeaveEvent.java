package org.ritsuka.youji.muc.event;

import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * Date: 9/30/11
 * Time: 9:51 PM
 */
public interface ForcedMUCLeaveEvent {
    public MultiUserChat getChat();

    public String getReason();

    public String getKicker();

    public String action();
}
