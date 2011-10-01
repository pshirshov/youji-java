package org.ritsuka.youji.util.yaconfig;

/**
 * Date: 10/1/11
 * Time: 4:31 PM
 */
public interface IVerifier<T> {
    class True<T> implements IVerifier<T> {
        @Override
        public boolean verify(T value) {
            return true;
        }
    }

    boolean verify(T value);
}
