package org.ritsuka.youji;

import org.ritsuka.youji.util.yaconfig.IVerifier;
import org.ritsuka.youji.util.yaconfig.TypedConfigKey;

import java.util.List;


/**
 * Date: 10/1/11
 * Time: 3:46 PM
 */
public final class Config<T> extends TypedConfigKey<T> {
    public static final Config<List<String>> INITIAL_ACCOUNTS = new Config<List<String>>(
            null, "youji.initial-accounts",
            new IVerifier.ListVerifier<String>(){});

    private Config(final T defaultValue, final String path) {
        super(defaultValue, path);
    }

    private Config(final T defaultValue, final String path, IVerifier<T> verifier) {
        super(defaultValue, path, verifier);
    }
}
