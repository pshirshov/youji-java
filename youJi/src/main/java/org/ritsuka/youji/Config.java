package org.ritsuka.youji;

import org.ritsuka.youji.util.yaconfig.ConfigKey;
import org.ritsuka.youji.util.yaconfig.IKeyVerifier;

import java.net.URI;
import java.util.List;


/**
 * Date: 10/1/11
 * Time: 3:46 PM
 */
public final class Config {
    public static final ConfigKey<List<String>> INITIAL_ACCOUNTS = new ConfigKey<List<String>>("youji.initial-accounts",
            new IKeyVerifier.ListVerifier<String>(){});

    public static final ConfigKey<Integer> MUC_REJOIN_INTERVAL = new ConfigKey<Integer>("youji.muc.rejoin.interval");

    public static final ConfigKey<Integer> MUC_REJOIN_MAXINTERVAL = new ConfigKey<Integer>("youji.muc.rejoin.maxinterval");


    public static final ConfigKey<URI> DB_LOGS = new ConfigKey<URI>("youji.db.logs");

    public static final ConfigKey<URI> DB_CONFIGS = new ConfigKey<URI>("youji.db.config");
}
