package org.ritsuka.natsuo.yaconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    private final AtomicReference<IKeyVerifier<T>> verifier =
            new AtomicReference<IKeyVerifier<T>>(new IKeyVerifier.True<T>());
    private final AtomicReference<IConstructor<T>> constructor =
            new AtomicReference<IConstructor<T>>(new IConstructor.Bypass<T>());

    public ConfigKey(final String path) {
        this(path, null);
    }

    public ConfigKey(final String path, final T defaultValue) {
        this.defValue = defaultValue;
        initPath(path);
    }

    public IKeyVerifier<T> getVerifier(){
        return verifier.get();
    }

    @Override
    public IConfigKey<T> setVerifier(final IKeyVerifier<T> verifier) {
        this.verifier.set(verifier);
        return this;
    }

    @Override
    public IConfigKey<T> setConstructor(final IConstructor<T> constructor) {
        this.constructor.set(constructor);
        return this;
    }

    @Override
    public IConstructor<T> getConstructor() {
        return constructor.get();
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
