package net.rainbowcreation.rainbowcreationx.gui;

import net.kyori.adventure.text.Component;
import net.rainbowcreation.rainbowcreationx.RainBowCreationX;
import net.rainbowcreation.rainbowcreationx.chat.Chat;
import net.rainbowcreation.rainbowcreationx.utils.item.Item;
import net.rainbowcreation.rainbowcreationx.utils.math.Lst;
import net.rainbowcreation.rainbowcreationx.utils.math.Roman;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Gui {
    private static final RainBowCreationX plugin = RainBowCreationX.getInstance();
    private static final FileConfiguration config = plugin.guiData.getConfig();
    private final String defaultName = "<white>";
    private Component name;
    private final int n = 54;
    private int guiId;
    private List<List<Integer>> numSet;
    private final Item item = new Item();
    private Inventory inv = null;
    private Player player = null;
    private static Gui instance;

    public Gui(int guiID) {
        instance = this;
        this.guiId = guiID;
        String id = Roman.serialize(guiID);
        name = Chat.legacyComponent(config.getString(id +".name"));
        item.material((Material.matchMaterial(config.getString(id+".material"))));
        if (config.contains(id+".link")) {
            int lid = config.getInt(id+".link");
            item.customModelData(lid);
            numSet = getNumset(lid);
            if (config.contains(id+".grid"))
                numSet = Lst.overrideList(numSet, getNumset(guiID));
        } else {
            item.customModelData(guiID);
            numSet = getNumset(guiID);
        }
        item.displayName(defaultName);
    }
    public static FileConfiguration getConfig() {
        return config;
    }
    public static int getID(Inventory inv) {
        return inv.getItem(44).getItemMeta().getCustomModelData();
    }
    public static List<List<Integer>> getNumset(int guiID) {
        return  (List<List<Integer>>) config.getList(Roman.serialize(guiID)+".grid");
    }
    public static boolean getCancelled(int guiID) {
        return config.getBoolean(Roman.serialize(guiID)+".cancelled");
    }
    public Component getName() {
        return name;
    }
    public Gui setName(Component name) {
        this.name = name;
        return this;
    }
    public Gui setPlayer(Player player) {
        this.player = player;
        return this;
    }
    public Player getPlayer() {
        return player;
    }
    public Inventory getInv() {
        return inv;
    }
    public Gui setInv(Inventory inv) {
        this.inv = inv;
        return this;
    }
    public Gui gridReader() {
        inv = Bukkit.createInventory(null, n, Chat.componentColored(Chat.plainComponent("[GUI]  ").append(name)));
        for (int i = 0; i<(n/9); i++) {
            List<Integer> tmp = numSet.get(i);
            for (int j = 0; j<9; j++){
                ItemStack k = guiPart(tmp.get(j));
                inv.setItem(Lst.toSlot(i, j), k);
            }
        }
        inv.setItem(44, item.get());
        return this;
    }
    public static ItemStack guiPart(int guiID) {
        String id = Roman.serialize(guiID);
        Item item = new Item();
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
            item.lore(Lst.minimessageColoredList((List<String>) config.getList(id + ".lore")));
        try {
            Class<?> gui = Class.forName("net.rainbowcreation.rainbowcreationx.gui." + config.getString(id + ".name") + "Gui");
            Object[] obj = {instance, item};
            Class<?>[] params = new Class[obj.length];
            params[0] = Gui.class;
            params[1] = Item.class;
            item = (Item) gui.getDeclaredMethod("runSelf", params).invoke(gui.newInstance(), obj);
        } catch (Exception ignored) {}
        return item.get();
    }
    public Inventory get() {
        if (inv == null)
            gridReader();
        try {
            Class<?> gui = Class.forName("net.rainbowcreation.rainbowcreationx.gui."+config.getString(Roman.serialize(guiId)+".name")+"Gui");
            inv = (Inventory) gui.getDeclaredMethod("run", Gui.class).invoke(gui.newInstance(), instance);
        } catch (Exception ignored) {}
        return inv;
    }
}
