package org.ritsuka.youji.muc;

/**
 * Date: 9/29/11
 * Time: 9:15 PM
 */
public final class MucData {
    private String nick = null;

    public MucData() {
        nick = "YouJI";
    }

    public String roomJid() {
        return "aconf@conference.jabber.ru";
    }

    public String password() {
        return "oblavobla";
    }

    public String nick() {
        return nick;
    }

    public String goodNick(final String rejected) {
        return rejected + "_";
    }
}
