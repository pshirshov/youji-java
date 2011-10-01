package org.ritsuka.youji.util.yaconfig;

/**
 * Date: 10/1/11
 * Time: 4:31 PM
 */
abstract public class Verifier<T> {
    public abstract boolean verify(T value);
}
