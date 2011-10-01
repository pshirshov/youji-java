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

    public TypedConfigKey(final T defaultValue, final String... path) {
        this.defValue = defaultValue;
        for (String pathseg:path) {
            if (pathseg.contains("."))
                Collections.addAll(keys, pathseg.split("\\."));
            else
                keys.add(pathseg);
        }
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
