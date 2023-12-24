package net.rainbowcreation.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.bungee.Server;
import net.rainbowcreation.core.api.config.Config;
import net.rainbowcreation.core.api.utils.*;
import net.rainbowcreation.core.event.Handler;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.PublicKey;

public class Core extends JavaPlugin {
    private static Core ps_instance;

    public static Core PS_getInstance() {
        return ps_instance;
    }
    public Essentials P_ess;
    private ProtocolManager p_protocolManager;
    public String P_version;
    public Config P_config_gui;
    public Console P_console;
    public GuiHolder P_guiHolder;
    public Warp P_warp;
    public Server P_bungee;

    @Override
    public void onEnable() {
        ps_instance = this;
        P_console = new Console(this);
        Str.header(Reference.NAME+":"+Reference.VERSION,P_console);
        final PluginManager F_manager = Bukkit.getPluginManager();
        P_ess = (Essentials) ps_instance.getServer().getPluginManager().getPlugin("Essentials");
        if (P_ess == null) {
            P_console.info("Please install Essentials"); //checker
            F_manager.disablePlugin(ps_instance);
        }
        p_protocolManager = ProtocolLibrary.getProtocolManager();
        final String F_package_name = ps_instance.getServer().getClass().getPackage().getName();
        P_version = F_package_name.substring(F_package_name.lastIndexOf('.') + 1);
        P_console.info("Loading support for " + P_version);
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        saveDefaultConfig();
        P_config_gui = new Config(ps_instance, "gui.yml", P_console);
        P_config_gui.saveConfig();
        P_bungee = new Server(ps_instance, getServer());
        P_guiHolder = new GuiHolder();
        final Gui F_gui = new Gui(ps_instance, P_config_gui.getConfig());
        P_warp = new Warp(ps_instance, P_ess);
        final Handler F_handler = new Handler();
        F_handler.register(F_manager, ps_instance);
        /*
        p_protocolManager.addPacketListener(new PacketAdapter(ps_instance) {
            @Override
            public void onPacketSending(PacketEvent event) {
                // Intercept outgoing packets (client to server)
            }

            @Override
            public void onPacketReceiving(PacketEvent event) {
                // Intercept incoming packets (server to client)
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                P_console.info(packet.getStrings().read(0));
                // Check if it's a Handshake packet (status change in 1.8)
            }
        });

         */
    }

    private void addRecipe() {
        P_console.info("register barier recipe");
        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = barrier.getItemMeta();
        itemMeta.setDisplayName("MainMenu");
        barrier.setItemMeta(itemMeta);
        ShapedRecipe bar = new ShapedRecipe(barrier);
        bar.shape("  ","  ");
        getServer().addRecipe(bar);
        /*
        ItemStack air = new ItemStack(Material.AIR, 1);
        ShapedRecipe a = new ShapedRecipe(air);
        a.shape("   ","   ","   ");
        getServer().addRecipe(a);
        P_console.info("register done!");
         */
    }
}
