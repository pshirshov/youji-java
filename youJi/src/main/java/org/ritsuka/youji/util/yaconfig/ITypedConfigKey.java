package org.ritsuka.youji.util.yaconfig;

import java.util.List;

public interface ITypedConfigKey<T> {
    T getDefaultValue();

    List<String> getPath();
}
