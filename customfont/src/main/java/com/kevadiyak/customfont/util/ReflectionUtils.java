package com.kevadiyak.customfont.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The type Reflection utils.
 */
public class ReflectionUtils {
    private static final String TAG = ReflectionUtils.class.getSimpleName();

    /**
     * Gets field.
     *
     * @param clazz     the clazz
     * @param fieldName the field name
     * @return the field
     */
    public static Field getField(Class clazz, String fieldName) {
        try {
            final Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    /**
     * Gets value.
     *
     * @param field the field
     * @param obj   the obj
     * @return the value
     */
    public static Object getValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException ignored) {
        }
        return null;
    }

    /**
     * Sets value.
     *
     * @param field the field
     * @param obj   the obj
     * @param value the value
     */
    public static void setValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException ignored) {
        }
    }

    /**
     * Gets method.
     *
     * @param clazz      the clazz
     * @param methodName the method name
     * @return the method
     */
    public static Method getMethod(Class clazz, String methodName) {
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    /**
     * Invoke method.
     *
     * @param object the object
     * @param method the method
     * @param args   the args
     */
    public static void invokeMethod(Object object, Method method, Object... args) {
        try {
            if (method == null) return;
            method.invoke(object, args);
        } catch (Exception e) {
            Log.d(TAG, "Can't invoke method using reflection", e);
        }
    }

    /**
     * Inject value to private field
     *
     * @param instance  the target of injection
     * @param fieldName injected field
     * @param value     injected value
     */
    public static void setPrivateField(Object instance, String fieldName, Object value) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            setField(field, instance, value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Get private field from object
     *
     * @param instance  the target object
     * @param fieldName name of field
     * @return field value
     */
    public static Object getPrivateField(Object instance, String fieldName) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            return getField(field, instance);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inject value to inherited field
     *
     * @param instance  the target of injection
     * @param fieldName injected field
     * @param value     injected value
     */
    public static void setParentField(Object instance, String fieldName, Object value) {
        final Field field = getParentField(instance.getClass(), fieldName);
        if (field == null) {
            throw new RuntimeException(new NoSuchFieldException());
        }
        setField(field, instance, value);
    }

    /**
     * Get field from object
     *
     * @param instance  the target object
     * @param fieldName name of field
     * @return field value
     */
    public static Object getParentField(Object instance, String fieldName) {
        final Field field = getParentField(instance.getClass(), fieldName);
        return getField(field, instance);
    }

    private static Object getField(Field field, Object instance) {
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setField(Field field, Object instance, Object value) {
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getParentField(Class<?> instance, String fieldName) {
        final Class<?> superclass = instance.getSuperclass();
        if (superclass == null) {
            throw new RuntimeException(new NoSuchFieldException());
        }
        Field field = fieldByName(superclass.getDeclaredFields(), fieldName);
        return field == null ? getParentField(superclass, fieldName) : field;
    }

    private static Field fieldByName(Field[] fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
