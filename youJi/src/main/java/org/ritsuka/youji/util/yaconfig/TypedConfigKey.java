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
public class TypedConfigKey<T> implements ITypedConfigKey<T> {
    final List<String> keys = new ArrayList<String>();
    final T defValue;
    final Verifier verifier;

    public TypedConfigKey(final T defaultValue, final String path) {
        verifier = null;
        this.defValue = defaultValue;
        initPath(defaultValue, path);
    }

    public TypedConfigKey(final T defaultValue, final String path, final Verifier verifier) {
        this.verifier = verifier;
        this.defValue = defaultValue;
        initPath(defaultValue, path);
    }

    public Verifier verifier()
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
