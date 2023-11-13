package net.rainbowcreation.core.eventmanager.playerjoin;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.chat.Chat;
import net.rainbowcreation.core.chat.console.Console;
import net.rainbowcreation.core.utils.permission.Permission;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class onPlayerJoin implements Listener {
    private static final Core plugin = Core.getInstance();
    private static final FileConfiguration config = plugin.playerData.getConfig();
    @EventHandler
    public static void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String prefix = "player."+uuid;
        Console.info(prefix);
        if (!config.contains(prefix)) {
            Console.info("New player detected!");
            for (String key:config.getConfigurationSection("template.UUID").getKeys(true)) {
                Console.info("Setup"+key);
                config.set(prefix+"."+key, config.get("template.UUID."+key));
            }
            config.set(prefix+".name", player.getName());
            plugin.playerData.saveConfig();
            plugin.reloadConfig();
        }
        if (!Permission.hasPermission(player, "rbc.verified")) {
            Chat.sendPlayerMessage(player, "<yellow>Warn! <white>you are <red>not verified <white>by server some feature may not work!");
        }
    }
}
