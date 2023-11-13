package net.rainbowcreation.core.eventmanager;

import net.rainbowcreation.core.eventmanager.asyncplayerchat.onChat;
import net.rainbowcreation.core.eventmanager.inventoryclick.GuiClick;
import net.rainbowcreation.core.eventmanager.inventoryclose.GuiClose;
import net.rainbowcreation.core.eventmanager.inventorydrag.GuiDrag;
import net.rainbowcreation.core.eventmanager.playerjoin.onPlayerJoin;
import net.rainbowcreation.core.eventmanager.playerswaphanditems.onPlayerSwapHandItem;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public static void register(PluginManager manager, Plugin instance) {
        manager.registerEvents(new onPlayerSwapHandItem(), instance);
        manager.registerEvents(new GuiClick(), instance);
        manager.registerEvents(new GuiClose(), instance);
        manager.registerEvents(new onPlayerJoin(), instance);
        manager.registerEvents(new GuiDrag(), instance);
        manager.registerEvents(new onChat(), instance);
    }
}
