package org.ritsuka.natsuo.yaconfig;

import org.ritsuka.natsuo.reflection.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ketoth xupack <ketoth.xupack@gmail.com>
 * @since 10/7/11 4:21 AM
 */
public abstract class SafeListTransformer<V, K>
        extends TypeReference<V>
        implements IConstructor<List<K>> {

    @SuppressWarnings("unchecked")
    @Override
    public final List<K> construct(final Object val) {
        if (!(val instanceof List)) {
            throw new IllegalArgumentException("incorrect parsers list type");
        }

        List rawParsers = (List) val;
        List<K> parsers = new ArrayList<K>();
        for (Object item : rawParsers) {
            try {
                if (item == null
                    || item.getClass().isAssignableFrom(getTypeClass())) {
                    parsers.add(transform((V) item));
                }
            } catch (final Exception e){
                throw new IllegalArgumentException(e);
            }
        }

        return parsers;
    }

    public abstract K transform(final V source);
}
