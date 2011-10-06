package org.ritsuka.natsuo.yaconfig;

import org.ritsuka.natsuo.reflection.IntrospectionUtils;
import org.ritsuka.natsuo.reflection.TypeReference;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This getConstructor allows to get java objects from yaml config.
 *
 * Example object declaration:
 * <tt>
 * test
 *   org.my.complex.long.namespace.Foo:
 *     new:
 *       - [param1]
 *       ...
 *       - [paramN]
 *     set:
 *       [name1]: [value1]
 *       ...
 *       [nameM]: [valueM]
 * </tt>
 *
 * Example usage:
 * <tt>
 *     ConfigKey<Foo> key = new ConfigKey<Foo>("test")
 *          .setConstructor(new ReflectConstructor<Foo>{});
 *     Foo fooObj = YaConfig.get(key);
 * </tt>
 *
 * The above code is somewhat equals to:
 * <tt>
 *     Foo foo = new org.my.complex.long.namespace.Foo(param1, ..., paramN);
 *     foo.setName1(value1);
 *     ...
 *     foo.setName1(valueM);
 * </tt>
 *
 * You can use other objects declarations in [param] and [value] too.
 *
 * Restrictions:
 * <ol>
 *     <li>Some collision may occur if you are passing {@link Map} to [param]
 *     and [value].</li>
 *     <li>At least one of "new"/"set" keywords should be declared.</li>
 * </ol>
 *
 * @author ketoth xupack <ketoth.xupack@gmail.com>
 * @since 10/2/11 5:37 PM
 */
public abstract class ReflectConstructor<T>
        extends TypeReference<T>
        implements IConstructor<T> {

    @SuppressWarnings("unchecked")
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

        // finding out class definition
        if (classDef.size() != 1) {
            throw abort("incorrect mapping found while parsing constructor");
        }
        Map.Entry objectDef = classDef.entrySet().iterator().next();

        Object classNameDef = objectDef.getKey();
        if (!(classNameDef instanceof String)) {
            throw abort("class name expected");
        }
        String className = (String) classNameDef;

        Object classModifiersDef = objectDef.getValue();
        if (!(classModifiersDef instanceof Map)) {
            throw abort("incorrect constructor definition found");
        }

        Map<?, ?> classModifiers = (Map) classModifiersDef;
        if (classModifiers.size() > 2
            || (!classModifiers.containsKey("new")
                && !classModifiers.containsKey("set"))) {
            throw abort("unexpected class modifiers found");
        }

        List<Object> params = new ArrayList<Object>();
        if (classModifiers.containsKey("new")) {
            Object constructorParamsDef = classModifiers.get("new");
            if (!(constructorParamsDef instanceof List)) {
                throw abort("unexpected constructor parameters found");
            }

            List constructorParams = (List) constructorParamsDef;
            for (Object paramDef : constructorParams) {
                Object param = paramDef;
                if (paramDef instanceof Map) {
                    param = construct((Map) paramDef);
                }
                params.add(param);
            }
        }

        Map<String, Object> setters = new LinkedHashMap<String, Object>();
        if (classModifiers.containsKey("set")) {
            Object settersDef = classModifiers.get("set");
            if (!(settersDef instanceof Map)) {
                throw abort("constructor parameters found");
            }

            Map<?, ?> settersParams = (Map) settersDef;
            for (Map.Entry setterDef : settersParams.entrySet()) {
                Object setterName = setterDef.getKey();
                Object setterVal = setterDef.getValue();

                if (!(setterName instanceof String)) {
                    throw abort("unexpected setter name: " + setterDef);
                }
                setters.put((String) setterName, setterVal);
            }
        }

        Object newObject = makeObject(className,
                params.toArray(new Object[params.size()]));
        makeSetters(newObject, setters);
        return newObject;
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

    private static String capitalize(final String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    private void makeSetters(final Object obj,
                             final Map<String, Object> setters)
            throws InvocationTargetException, NoSuchMethodException,
                   InstantiationException, IllegalAccessException,
                   ClassNotFoundException {
        for (Map.Entry<String, Object> setter : setters.entrySet()) {
            String methodName = "set" + capitalize(setter.getKey());
            Object paramDef = setter.getValue();
            Object[] params = new Object[]{paramDef};
            if (paramDef instanceof List) {
                params = ((List) paramDef).toArray();
            }

            try {
                IntrospectionUtils.invokeCompatibleMethod(obj, methodName, params);
            } catch (final Throwable e) {
                if (paramDef instanceof Map) {
                    IntrospectionUtils.invokeCompatibleMethod(obj, methodName,
                            new Object[]{construct((Map) paramDef)});
                    continue;
                }
                throw new IllegalStateException(e);
            }
        }
    }

}
