package org.ritsuka.youji.util.yaconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 10/1/11
 * Time: 3:48 PM
 */

/*
* You should subclass this defining constructor which just calls this class' one
* and then declare config entries in format like
*        public static final Config<String> HIBERNATE_CONFIG = new Config<String>(
*            "conf/hibernate/hibernate.cfg.xml",
*            "hibernate", "test", "config.value");
*  path ("hibernate", "test", "config.value") is equal to "hibernate.test.config.value"
* */
public class ConfigKey<T> implements IConfigKey<T> {
    final List<String> keys = new ArrayList<String>();
    final T defValue;
    final IKeyVerifier<T> verifier;

    public ConfigKey(final String path) {
        this(path, null, new IKeyVerifier.True<T>());
    }

    public ConfigKey(final String path, final T defaultValue) {
        this(path, defaultValue, new IKeyVerifier.True<T>());
    }

    public ConfigKey(final String path, final IKeyVerifier<T> verifier) {
        this(path, null, verifier);
    }

    public ConfigKey(final String path, final T defaultValue, final IKeyVerifier<T> verifier) {
        this.verifier = verifier;
        this.defValue = defaultValue;
        initPath(defaultValue, path);
    }

    public IKeyVerifier<T> verifier()
    {
        return verifier;
    }
    private void initPath(T defaultValue, String path) {
        if (path.contains("."))
            Collections.addAll(keys, path.split("\\."));
        else
            keys.add(path);
    }

    @Override
    public T getDefaultValue() {
        return defValue;
    }

    @Override
    public List<String> getPath() {
        return keys;
    }
}
