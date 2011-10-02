package org.ritsuka.youji.util.yaconfig;

/**
 * @author ketoth xupack <ketoth.xupack@gmail.com>
 * @since 10/2/11 5:36 PM
 */
public interface IConstructor<T> {
    class Bypass<T> implements IConstructor<T> {
        @SuppressWarnings("unchecked")
        @Override
        public T construct(Object val) {
            return (T) val;
        }
    }

    T construct(Object val);
}
