package net.rainbowcreation.core.api.utils;

public class Remap {
    public static Object cast(Class<?> dstclass, String version, String srcname) {
        try {
            final Class<?> F_clazz = Class.forName("net.rainbowcreation.core." + version + "." + srcname);
            if (dstclass.isAssignableFrom(F_clazz)) {
                return dstclass.newInstance();
            }
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Failed to create an instance of " + dstclass.getSimpleName());
            e.printStackTrace();
        }
        return null;
    }
}
