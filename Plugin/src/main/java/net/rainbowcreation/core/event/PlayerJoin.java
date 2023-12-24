package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    public void onJoin(PlayerJoinEvent event) {
        final Player F_player = event.getPlayer();
        final Core P_core = Core.PS_getInstance();
        if (P_core.P_playerlog.containsKey(F_player)) {
            P_core.P_playerlog.replace(F_player, true);
        }
        else
            P_core.P_playerlog.put(F_player, true);
    }
}
