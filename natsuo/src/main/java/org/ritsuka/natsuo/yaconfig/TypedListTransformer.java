package org.ritsuka.natsuo.yaconfig;

import org.ritsuka.natsuo.reflection.TypeReference;

/**
 * @author ketoth xupack <ketoth.xupack@gmail.com>
 * @since 10/7/11 11:38 AM
 */
public final class TypedListTransformer<T>
        extends  SafeListTransformer<Object, T> {

    public TypedListTransformer() {
        super(new TypeReference<Object>() {});
    }

    @SuppressWarnings("unchecked")
    @Override
    public T transform(Object source) {
        return (T) source;
    }
}
