package net.rainbowcreation.core.v1_20_R1.gui;

import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.IRegistry;

public class Gui implements IRegistry {
    public static IGui MAIN;
    @Override
    public void register() {
        MAIN = new Main();
    }

    @Override
    public Object get(String key) {
        switch (key) {
            case ("gui_main") : {
                return MAIN;
            }
        }
        return null;
    }
}
