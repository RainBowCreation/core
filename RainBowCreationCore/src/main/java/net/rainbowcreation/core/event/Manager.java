package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.plugin.PluginManager;

/**
 * event manager
 */
public final class Manager {
    private Manager() {
        // void
    }

    /**
     *
     * @param manager
     * @param instance
     */
    public static void register(PluginManager manager, Core instance) {
        manager.registerEvents(new net.rainbowcreation.core.event.inventory.click.Gui(instance), instance);
        manager.registerEvents(new net.rainbowcreation.core.event.inventory.close.Gui(instance), instance);
        manager.registerEvents(new net.rainbowcreation.core.event.inventory.drag.Gui(instance), instance);
    }
}
