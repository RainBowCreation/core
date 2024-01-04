package net.rainbowcreation.core.event.player;

import de.sportkanone123.clientdetector.spigot.api.ClientDetectorAPI;
import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.utils.Action;
import net.rainbowcreation.core.api.utils.VersionInfo;
import net.rainbowcreation.core.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Core.getInstance().playerlog.put(event.getPlayer(), true);
        Action.spawnFirework(player.getLocation());

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
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Core.getInstance().usePacketApi()) {
                    Core.getInstance().getConsole().info(ClientDetectorAPI.getPlayerClient(player) + ":" + PlayerUtils.getVersion(player) + ":isforge:" + ClientDetectorAPI.isForgePlayer(player) + ":isbedrock:" + ClientDetectorAPI.isBedrockPlayer(player));
                    if (ClientDetectorAPI.getPlayerClient(player).equals("Forge"))
                        cancel();
                    if (ClientDetectorAPI.isBedrockPlayer(player))
                        cancel();
                    if (ClientDetectorAPI.isForgePlayer(player))
                        cancel();
                }
                VersionInfo version = VersionInfo.parseVersion(PlayerUtils.getVersion(player));
                if (version == null)
                    cancel();
                if (version.getMajor() == 1) {
                    String url = "v1_";
                    int minor = version.getMinor();
                    if (minor < 11) {
                        // download manually
                    } else {
                        if (minor <= 12)
                            url += "11";
                        else if (minor <= 14)
                            url += "13";
                        else if (minor <= 16)
                            url += "15";
                        else
                            url += String.valueOf(minor);
                        player.setResourcePack("https://github.com/RainBowCreation/resourcepack/releases/latest/download/RainBowCreation_" + url + "_lite.zip");
                    }
                }
            }
        }.runTaskLater(Core.getInstance().getPlugin(), 100L);
    }
}
