package net.rainbowcreation.core.gui;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IEvent;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.utils.GuiHolder;
import net.rainbowcreation.core.api.utils.Remap;

public class Gui implements IRegistry {
    public static GuiHolder gui_holder;
    public static IRegistry instance;
    @Override
    public void register() {
        gui_holder = new GuiHolder();

        // other version register
        instance = (IRegistry) Remap.castInterface(IRegistry.class, Core.getInstance().version, "gui.Gui");
        if (instance == null)
            return;
        instance.register();
    }

    @Override
    public Object get(String key) {
        return instance;
    }
}
