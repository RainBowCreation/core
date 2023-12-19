package net.rainbowcreation.api.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * gui reader interface
 */
public class RGui {
    private static FileConfiguration config;
    private final String defaultName = "<white>";
    private Component name;
    private final int n = 54;
    private int guiId;
    private List<List<Integer>> numSet;
    private final RItem item = new RItem();
    private Inventory inv = null;
    private Player player = null;
    private static RGui instance;

    /**
     *
     * @param guiID
     */
    public RGui(int guiID, FileConfiguration guiconfig) {
        instance = this;
        this.guiId = guiID;
        final String id = RRoman.serialize(guiID);
        setConfig(guiconfig);
        name = RChat.legacyComponent(config.getString(id +".name"));
        item.material(Material.matchMaterial(config.getString(id+".material")));
        if (config.contains(id+".link")) {
            final int lid = config.getInt(id+".link");
            item.customModelData(lid);
            numSet = getNumset(lid);
            if (config.contains(id+".grid"))
                numSet = RLst.overrideList(numSet, getNumset(guiID));
        } else {
            item.customModelData(guiID);
            numSet = getNumset(guiID);
        }
        item.displayName(defaultName);
    }

    /**
     *
     * @param config
     */
    public void setConfig(FileConfiguration config) {
        RGui.config = config;
    }

    /**
     *
     * @return
     */
    public static FileConfiguration getConfig() {
        return config;
    }

    /**
     *
     * @param inv
     * @return
     */
    public static int getID(Inventory inv) {
        return inv.getItem(44).getItemMeta().getCustomModelData();
    }

    /**
     *
     * @param guiID
     * @return
     */
    public static List<List<Integer>> getNumset(int guiID) {
        return (List<List<Integer>>) config.getList(RRoman.serialize(guiID)+".grid");
    }

    /**
     *
     * @param guiID
     * @return
     */
    public static boolean getCancelled(int guiID) {
        return config.getBoolean(RRoman.serialize(guiID)+".cancelled");
    }

    /**
     *
     * @return
     */
    public Component getName() {
        return name;
    }

    /**
     *
     * @param name
     * @return
     */
    public RGui setName(Component name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @param player
     * @return
     */
    public RGui setPlayer(Player player) {
        this.player = player;
        return this;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return
     */
    public Inventory getInv() {
        return inv;
    }

    /**
     *
     * @param inv
     * @return
     */
    public RGui setInv(Inventory inv) {
        this.inv = inv;
        return this;
    }

    /**
     *
     * @return
     */
    public RGui gridReader() {
        inv = Bukkit.createInventory(null, n, RChat.componentColored(RChat.plainComponent("[GUI]  ").append(name)));
        for (int i = 0; i<(n/9); i++) {
            final List<Integer> tmp = numSet.get(i);
            for (int j = 0; j<9; j++){
                final ItemStack k = guiPart(tmp.get(j));
                inv.setItem(RLst.toSlot(i, j), k);
            }
        }
        inv.setItem(44, item.get());
        return this;
    }

    /**
     *
     * @param guiID
     * @return
     */
    public static ItemStack guiPart(int guiID) {
        final String id = RRoman.serialize(guiID);
        RItem item = new RItem();
        item.material(Material.matchMaterial(config.getString(id + ".material")));
        if (config.getBoolean(id+".custom")) {
            if ((guiID > 1 && guiID < 200) || guiID == 999)
                item.customModelData(1);
            else
                item.customModelData(guiID);
        }
        if (config.contains(id+".name"))
            item.displayName(config.getString(id+".name"));
        if (config.contains(id+".lore"))
            item.lore(RLst.minimessageColoredList((List<String>) config.getList(id + ".lore")));
        try {
            final Class<?> gui = Class.forName("net.rainbowcreation.core.gui." + config.getString(id + ".name") + "Gui");
            final Object[] obj = {instance, item};
            final Class<?>[] params = new Class[obj.length];
            params[0] = RGui.class;
            params[1] = RItem.class;
            item = (RItem) gui.getDeclaredMethod("runSelf", params).invoke(gui.newInstance(), obj);
        } catch (Exception ignored) {}
        return item.get();
    }

    /**
     *
     * @return
     */
    public Inventory get() {
        if (inv == null)
            gridReader();
        try {
            final Class<?> gui = Class.forName("net.rainbowcreation.core.gui."+config.getString(RRoman.serialize(guiId)+".name")+"Gui");
            inv = (Inventory) gui.getDeclaredMethod("run", RGui.class).invoke(gui.newInstance(), instance);
        } catch (Exception ignored) {}
        return inv;
    }
}
