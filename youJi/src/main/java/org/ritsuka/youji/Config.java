package org.ritsuka.youji;

import org.ritsuka.youji.util.yaconfig.TypedConfigKey;

import java.util.List;


/**
 * Date: 10/1/11
 * Time: 3:46 PM
 */
public final class Config<T> extends TypedConfigKey<T> {
    public static final Config<List> INITIAL_ACCOUNTS = new Config<List>(
            null, "youji.initial-accounts");

    private Config(final T defaultValue, final String... path) {
        super(defaultValue, path);
    }
}
