package org.ritsuka.natsuo.yaconfig;

import java.util.List;

public interface IConfigKey<T> {
    T getDefaultValue();
    IKeyVerifier<T> verifier();
    IConstructor<T> constructor();
    List<String> getPath();
}
