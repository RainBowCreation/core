package net.rainbowcreation.rainbowcreationx.eventmanager;

import net.rainbowcreation.rainbowcreationx.RainBowCreationX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class onChat implements Listener {
    private static final FileConfiguration config = RainBowCreationX.getInstance().playerData.getConfig();
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        String ori_msg = event.getMessage();
        String prefix = "player."+event.getPlayer().getUniqueId()+".runnable";
        switch (config.getString(prefix+".case")) {
            case "default":
                break;
            case "warp":
                config.set(prefix+".string", ori_msg);
                event.setCancelled(true);
            case "home":
                config.set(prefix+".string", ori_msg);
                event.setCancelled(true);
        }
    }

}
