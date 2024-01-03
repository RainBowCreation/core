package net.rainbowcreation.core.event.player;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.utils.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Core.getInstance().isLobby()) {
            Core.getInstance().playerlog.put(event.getPlayer(), true);
            Action.spawnFirework(player.getLocation());
        }
        if (player.hasPermission("rbc.glow")) {
            String team = "";
            if (player.hasPermission("rbc.glow.red")) {
                team = "red";
            } else if (player.hasPermission("rbc.glow.purple")) {
                team = "purple";
            } else if (player.hasPermission("rbc.glow.aqua")) {
                team = "aqua";
            } else if (player.hasPermission("rbc.glow.green")) {
                team = "green";
            }
            if (team.isEmpty())
                return;
            Team pteam = Core.getInstance().getPlugin().getServer().getScoreboardManager().getMainScoreboard().getTeam(team);
            if (pteam != null)
                pteam.addPlayer(player);
        }
    }
}
