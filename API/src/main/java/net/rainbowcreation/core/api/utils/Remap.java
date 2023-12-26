package net.rainbowcreation.core.api.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public class Remap {
    public static <T> Object cast(Class<?> dstclass, String version, String srcname) {
        try {
            final Class<?> clazz = Class.forName("net.rainbowcreation.core." + version + "." + srcname);
            if (dstclass.isAssignableFrom(clazz)) {
                @SuppressWarnings("unchecked")
                final Constructor<T> constructor = (Constructor<T>) dstclass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        } catch(InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.err.println("Failed to create an instance of " + srcname);
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Object castInterface(Class<?> dstinterface, String version, String srcname) {
        try {
            final Class<?> clazz = Class.forName("net.rainbowcreation.core." + version + "." + srcname);
            if (dstinterface.isAssignableFrom(clazz)) {
                final Constructor<T> constructor = (Constructor<T>) clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        } catch(InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.err.println("Failed to create an instance of " + srcname);
            e.printStackTrace();
        }
        return null;
    }
}
