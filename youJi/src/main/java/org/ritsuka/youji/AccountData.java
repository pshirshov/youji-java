package org.ritsuka.youji;

import org.ritsuka.youji.muc.ConferenceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 9/29/11
 * Time: 8:30 PM
 */
public class AccountData {
    private String login = null;

    AccountData(String a_login) {
        login = a_login;
    }

    public String login() {
        return login;
    }

    public String password() {
        return "BNasdfhk87623#";
    }

    public String server() {
        return "draugr.de";
    }

    public String resource() {
        return "hooita";
    }

    /*public String alternativeResource(String rejected) {
        return rejected + "_";
    }*/

    public List<ConferenceData> conferences() {
        ArrayList<ConferenceData> conferenceDatas = new ArrayList<ConferenceData>();
        conferenceDatas.add(new ConferenceData());
        return conferenceDatas;
    }

    public String toString() {
        return login() + "@" + server() + "/" + Integer.toHexString(hashCode());
    }

}
