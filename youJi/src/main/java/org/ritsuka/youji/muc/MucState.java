package org.ritsuka.youji.muc;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.ritsuka.youji.Config;
import org.ritsuka.youji.util.yaconfig.YaConfig;

/**
 * Date: 9/29/11
 * Time: 11:35 PM
 */
public final class MucState {

    private MucData confdata = null;
    private Integer attemptsCount = 0;
    private MultiUserChat muc = null;
    private String lastNick = null;
    private Boolean nickConflict = false;

    public MucState(final MucData a_data,
                    final MultiUserChat a_chat) {
        confdata = a_data;
        muc = a_chat;
    }

    public MucData conferenceData() {
        return confdata;
    }

    public Integer attempts() {
        return attemptsCount;
    }

    public Integer pauseBeforeNextAttempt() {
        Integer pause = attempts() * YaConfig.get(Config.MUC_REJOIN_INTERVAL);
        Integer maxInterval = YaConfig.get(Config.MUC_REJOIN_MAXINTERVAL);
        if (pause > maxInterval)
            pause = maxInterval;
        return pause;
    }


    public MultiUserChat muc() {
        return muc;
    }

    public void newAttempt() {
        if (null == lastNick)
            lastNick = confdata.nick();
        ++attemptsCount;
    }

    public void success() {
        attemptsCount = 0;
        nickConflict = false;
    }

    public void nickConflict() {
        nickConflict = true;
    }

    public String nick() {
        if (nickConflict)
            return confdata.goodNick(lastNick);
        return confdata.nick();
    }
}
