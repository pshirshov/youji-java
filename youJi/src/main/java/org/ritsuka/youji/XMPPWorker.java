package org.ritsuka.youji;

import akka.actor.UntypedActor;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.ArrayList;

/**
 * Date: 9/29/11
 * Time: 7:51 PM
 */
public class XMPPWorker extends UntypedActor {
    private Connection connection = null;

    private void logon(AccountData account) throws XMPPException {
        if (null == connection)
        {
            System.out.println("Logging in");
            connection = new XMPPConnection(account.server());
            connection.connect();
            connection.login(account.login(), account.password(), account.resource());

            ArrayList<ConferenceData> conferences = account.conferences();
            for (ConferenceData conf:conferences)
            {
                MultiUserChat muc = new MultiUserChat(connection, conf.roomJid());
                if (conf.password() != null)
                    muc.join(conf.nick(), conf.password());
                else
                    muc.join(conf.nick());
                muc.addMessageListener(new MUCMessageListener(muc.getNickname()));

            }
        }
    }
       // message handler
  public void onReceive(Object message) throws Exception  {
    if (message instanceof AccountData) {
        System.out.println("worker message: " + message.toString());
      AccountData account = (AccountData) message;
      logon(account);
/*
      // perform the work
      double result = calculatePiFor(work.getStart(), work.getNrOfElements());

      // reply with the result
      getContext().replyUnsafe(new Result(result));*/

    } else
        throw new IllegalArgumentException("Unknown message [" + message + "]");
  }

}
