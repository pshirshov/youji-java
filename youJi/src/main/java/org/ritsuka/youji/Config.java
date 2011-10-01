package org.ritsuka.youji;

import org.ritsuka.youji.util.yaconfig.TypedConfigKey;
import org.ritsuka.youji.util.yaconfig.Verifier;

import java.util.List;


/**
 * Date: 10/1/11
 * Time: 3:46 PM
 */
public final class Config<T> extends TypedConfigKey<T> {
    public static final Config<List> INITIAL_ACCOUNTS = new Config<List>(
            null, "youji.initial-accounts",
            new Verifier<List>() {
                @Override
                public boolean verify(List value) {
                    for (Object obj:value)
                    {
                        if (!(obj instanceof String))
                            return false;
                    }
                    return true;
                }
           });

    private Config(final T defaultValue, final String path) {
        super(defaultValue, path);
    }

    private Config(final T defaultValue, final String path, Verifier<T> verifier) {
        super(defaultValue, path, verifier);
    }
}
