package org.ritsuka.youji.util.yaconfig;

import java.util.List;

public interface IConfigKey<T> {
    T getDefaultValue();
    IKeyVerifier<T> verifier();

    List<String> getPath();
}
