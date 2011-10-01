package org.ritsuka.youji.util;

import java.util.List;

public interface ITypedConfigKey<T> {
    T getDefaultValue();

    List<String> getPath();
}
