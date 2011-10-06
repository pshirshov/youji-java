package org.ritsuka.natsuo.yaconfig;

import java.util.List;

public interface IConfigKey<T> {
    T getDefaultValue();
    IKeyVerifier<T> getVerifier();
    IConfigKey<T> setVerifier(IKeyVerifier<T> verifier);
    IConstructor<T> getConstructor();
    IConfigKey<T> setConstructor(IConstructor<T> constructor);
    List<String> getPath();
}
