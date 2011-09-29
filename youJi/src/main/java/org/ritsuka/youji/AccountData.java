package org.ritsuka.youji;

import java.util.ArrayList;

/**
 * Date: 9/29/11
 * Time: 8:30 PM
 */
public class AccountData {
    public String login()
    {
        return   "youji_sagan";
    }
    public String password()
    {
        return "BNasdfhk87623#";
    }
    public String server()
    {
        return "draugr.de";
    }

    public String resource()
    {
        return "hooita";
    }

    public String alternativeResource(String rejected)
    {
        return rejected + "_";
    }

    public ArrayList<ConferenceData> conferences()
    {
        ArrayList<ConferenceData> conferenceDatas =  new ArrayList<ConferenceData>();
        conferenceDatas.add(new ConferenceData());
        conferenceDatas.add(new ConferenceData());
        return conferenceDatas;
    }
}
