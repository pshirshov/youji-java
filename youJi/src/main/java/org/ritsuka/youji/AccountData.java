package org.ritsuka.youji;

import org.jivesoftware.smack.util.StringUtils;
import org.ritsuka.youji.muc.MucData;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 9/29/11
 * Time: 8:30 PM
 */
public final class AccountData {
    private final String login;
    private final String server;
    private final String resource;
    private final String password;

    AccountData(final String a_fqjid) {
        this.server = StringUtils.parseServer(a_fqjid);
        this.resource = StringUtils.parseResource(a_fqjid);
        String loginpass = StringUtils.parseName(a_fqjid);
        assert loginpass.contains(":");
        int delimpos = loginpass.indexOf(':');
        this.login = loginpass.substring(0, delimpos);
        this.password = loginpass.substring(delimpos+1);
    }

    public String login() {
        return login;
    }

    public String password() {
        return password;
    }

    public String server() {
        return server;
    }

    public String resource() {
        return resource;
    }

    public List<MucData> conferences() {
        ArrayList<MucData> conferenceDatas = new ArrayList<MucData>();
        conferenceDatas.add(new MucData());
        return conferenceDatas;
    }

    public String toString() {
        return login() + "@" + server() + "/" + resource() + "=" + Integer.toHexString(hashCode());
    }

}
