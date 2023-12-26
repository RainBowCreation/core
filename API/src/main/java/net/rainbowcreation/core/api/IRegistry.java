package net.rainbowcreation.core.api;

public interface IRegistry {
    void register();

    Object get(String key);
}
