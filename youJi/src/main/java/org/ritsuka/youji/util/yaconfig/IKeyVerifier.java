package org.ritsuka.youji.util.yaconfig;


import org.ritsuka.youji.util.reflection.TypeReference;

import java.util.List;

/**
 * Date: 10/1/11
 * Time: 4:31 PM
 */
public interface IKeyVerifier<T> {
    boolean verify(T value);

    final class True<T> implements IKeyVerifier<T> {
        @Override
        public boolean verify(final T value) {
            return true;
        }
    }

    abstract class ListVerifier<V>
            extends TypeReference<V>
            implements IKeyVerifier<List<V>> {
        @Override
        public final boolean verify(final List value) {
            for (Object obj: value) {
                if (obj !=null && obj.getClass() != getTypeClass())
                    return false;
            }
            return true;
        }
    }
}
