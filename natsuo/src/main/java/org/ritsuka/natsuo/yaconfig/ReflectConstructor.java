package org.ritsuka.natsuo.yaconfig;

import org.ritsuka.natsuo.reflection.IntrospectionUtils;
import org.ritsuka.natsuo.reflection.TypeReference;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ketoth xupack <ketoth.xupack@gmail.com>
 * @since 10/2/11 5:37 PM
 */
public abstract class ReflectConstructor<T>
        extends TypeReference<T>
        implements IConstructor<T> {

    @Override
    public final T construct(final Object val) {
        if (!(val instanceof Map)) {
            throw abort("mapping expected");
        }

        try {
            return (T) construct((Map) val);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Object construct(final Map<?, ?> classDef)
            throws ClassNotFoundException, InvocationTargetException,
                   NoSuchMethodException, IllegalAccessException,
                   InstantiationException {
        if (classDef.size() != 1) {
            throw abort("incorrect mapping found while parsing constructor");
        }

        Map.Entry constructorDef = classDef.entrySet().iterator().next();

        Object classNameDef = constructorDef.getKey();
        Object constructorParamsDef = constructorDef.getValue();

        if (!(classNameDef instanceof String) ||
            !(constructorParamsDef instanceof List)) {
            throw abort("incorrect constructor definition found");
        }

        String className = (String) classNameDef;
        List constructorParams = (List) constructorParamsDef;

        List<Object> params = new ArrayList<Object>();
        for (Object paramDef : constructorParams) {
            Object param = paramDef;
            if (paramDef instanceof Map) {
                param = construct((Map) paramDef);
            }
            params.add(param);
        }

        return makeObject(className,
                params.toArray(new Object[params.size()]));
    }

    private IllegalArgumentException abort(final String message) {
        return new IllegalArgumentException(
                message + " for [" + getTypeClass().getCanonicalName() + ']');
    }

    private Object makeObject(final String className, final Object... args)
            throws InvocationTargetException, NoSuchMethodException,
                   InstantiationException, IllegalAccessException,
                   ClassNotFoundException {
        Class<?> passedClazz = Class.forName(className);
        return IntrospectionUtils.newCompatibleInstance(passedClazz, args);
    }
}
