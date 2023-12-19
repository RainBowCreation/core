package net.rainbowcreation.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.earth2me.essentials.Essentials;
import net.rainbowcreation.api.utils.RConfig;
import net.rainbowcreation.api.utils.RConsole;
import net.rainbowcreation.api.utils.RGui;
import net.rainbowcreation.api.utils.RString;
import net.rainbowcreation.core.event.Manager;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
// import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * core
 */
public class Core extends JavaPlugin {
    private static Core instance;
    private ProtocolManager protocolManager;
    public RConsole console;
    public static Essentials ess;
    public RConfig guiData;
    public RConfig playerData;
    public ArrayList<Player> playersWithOpenInventory = new ArrayList<>();

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        instance = this;
        console = new RConsole(Bukkit.getConsoleSender());
        RString.header(Reference.NAME + ":" + Reference.VERSION, console);
        final PluginManager manager = Bukkit.getPluginManager();
        ess = (Essentials) manager.getPlugin("Essentials");
        if (ess == null) {
            console.info("Essentials not Installed please Install and try again");
            Bukkit.getServer().shutdown();
        }
        // Service.initialize();
        setupConfig();
        Manager.register(manager, instance);
        protocolManager.addPacketListener(new PacketAdapter(this,
                ListenerPriority.NORMAL,
                PacketType.Play.Client.CLIENT_COMMAND) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.CLIENT_COMMAND) {
                    final PacketContainer packet = event.getPacket();
                    final EnumWrappers.ClientCommand action = packet.getClientCommands().read(0);
                    console.info(action.toString());

                    if (action == EnumWrappers.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                        //Bukkit.broadcastMessage("someone open inv");
                        // Here is my player inventory open event
                        event.setCancelled(true);
                        final Player player = event.getPlayer();
                        player.openInventory(new RGui(2, guiData.getConfig()).setPlayer(player).get());
                    }
                }
            }
        });
        runnable();
    }

    private void runnable() {
        /*
        final Runnable a = () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                console.info(player.getOpenInventory().getType().toString());
                if (player.getOpenInventory().getType() == InventoryType.PLAYER) {
                    if (playersWithOpenInventory.contains(player))
                        continue;
                    playersWithOpenInventory.add(player);
                } else {
                    playersWithOpenInventory.remove(player);
                }
            }
        };
        final int taskID_a = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, a, 0, 1);
        final Runnable b = () -> {
            for (Player player : playersWithOpenInventory)
                console.info(player.getDisplayName() + " Open their inv");
        };
        final int taskID_b = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, a, 0, 20);
         */
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getScheduler().cancelTasks(instance);
        // save and disable data
    }

    private void setupConfig() {
        saveDefaultConfig();
        guiData = new RConfig(instance, "gui.yml", console);
        guiData.saveConfig();
        playerData = new RConfig(instance, "userdata.yml", console);
        playerData.saveConfig();
        playerData.reloadConfig();
    }

    private boolean setupManager() {
        // do setup here
        return true;
    }
}
