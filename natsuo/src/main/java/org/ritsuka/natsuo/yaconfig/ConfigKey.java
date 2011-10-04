package org.ritsuka.natsuo.yaconfig;

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
*/
public final class ConfigKey<T> implements IConfigKey<T> {
    private final List<String> keys = new ArrayList<String>();
    private final T defValue;
    private final IKeyVerifier<T> verifier;
    private final IConstructor<T> constructor;

    public ConfigKey(final String path) {
        this(path, new IKeyVerifier.True<T>());
    }

    public ConfigKey(final String path, final IConstructor<T> constructor) {
        this(path, new IKeyVerifier.True<T>(), constructor);
    }

    public ConfigKey(final String path, final T defaultValue) {
        this(path, defaultValue, new IKeyVerifier.True<T>(), new IConstructor.Bypass<T>());
    }

    public ConfigKey(final String path,
                     final IKeyVerifier<T> verifier) {
        this(path, verifier, new IConstructor.Bypass<T>());
    }

    public ConfigKey(final String path,
                     final IKeyVerifier<T> verifier,
                     final IConstructor<T> constructor) {
        this(path, null, verifier, constructor);
    }

    public ConfigKey(final String path, final T defaultValue,
                     final IKeyVerifier<T> verifier,
                     final IConstructor<T> constructor) {
        this.verifier = verifier;
        this.defValue = defaultValue;
        this.constructor = constructor;
        initPath(path);
    }

    public IKeyVerifier<T> verifier(){
        return verifier;
    }

    @Override
    public IConstructor<T> constructor() {
        return constructor;
    }

    private void initPath(final String path) {
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
