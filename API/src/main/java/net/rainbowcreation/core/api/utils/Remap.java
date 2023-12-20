package net.rainbowcreation.core.api.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public class Remap {
    public static <T> Object cast(Class<?> dstclass, String version, String srcname) {
        try {
            final Class<?> F_clazz = Class.forName("net.rainbowcreation.core." + version + "." + srcname);
            if (dstclass.isAssignableFrom(F_clazz)) {
                @SuppressWarnings("unchecked")
                final Constructor<T> F_constructor = (Constructor<T>) dstclass.getDeclaredConstructor();
                F_constructor.setAccessible(true);
                return F_constructor.newInstance();
            }
        } catch(InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.err.println("Failed to create an instance of " + srcname);
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Object castInterface(Class<?> dstinterface, String version, String srcname) {
        try {
            final Class<?> F_clazz = Class.forName("net.rainbowcreation.core." + version + "." + srcname);
            if (dstinterface.isAssignableFrom(F_clazz)) {
                final Constructor<T> F_constructor = (Constructor<T>) F_clazz.getDeclaredConstructor();
                F_constructor.setAccessible(true);
                return F_constructor.newInstance();
            }
        } catch(InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.err.println("Failed to create an instance of " + srcname);
            e.printStackTrace();
        }
        return null;
    }
}
