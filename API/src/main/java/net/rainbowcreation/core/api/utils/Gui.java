package net.rainbowcreation.core.api.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class Gui {
    private static Plugin pS_plugin;
    private static FileConfiguration pS_config;
    private final String pF_default_name = "<white>";
    private Component p_name;
    private int p_n = 54;
    private int p_gui_id;
    private String p_roman;
    private List<List<Integer>> p_num_set;
    private final Item pF_item = new Item();
    private Inventory p_inv = null;
    private Player p_player = null;
    private final Gui pF_instance;

    /**
     * First time calling this class need to setup plugin & config followed by .setID()
     * Next time you can use Gui(id)
     * @param plugin
     * @param guiconfig
     */
    public Gui(Plugin plugin, FileConfiguration guiconfig) {
        pF_instance = this;
        pS_plugin = plugin;
        pS_config = guiconfig;
    }

    public Gui(Plugin plugin, FileConfiguration guiconfig, int id) {
        pF_instance = this;
        pS_plugin = plugin;
        pS_config = guiconfig;
        setId(id);
    }

    public Gui(int id) {
        pF_instance = this;
        setId(id);
    }

    public Gui setId(int id) {
        p_gui_id = id;
        p_roman = Roman.S_serialize(id);
        p_name = Chat.S_legacyComponent(pS_config.getString(p_roman +".name"));
        pF_item.material((Material.matchMaterial(Objects.requireNonNull(pS_config.getString(p_roman + ".material")))));
        p_num_set = getNumset();
        if (pS_config.contains(p_roman +".link")) {
            final int F_lid = pS_config.getInt(p_roman +".link");
            pF_item.customModelData(F_lid);
            if (pS_config.contains(p_roman +".grid"))
                p_num_set = Lst.S_overrideList(p_num_set, getNumset());
        } else {
            pF_item.customModelData(p_gui_id);
        }
        pF_item.displayName(pF_default_name);
        return pF_instance;
    }

    public static FileConfiguration S_getConfig() {
        return pS_config;
    }
    public static int S_getID(Inventory inv) {
        return Objects.requireNonNull(Objects.requireNonNull(inv.getItem(44)).getItemMeta()).getCustomModelData();
    }

    public int getID(Inventory inv) {
        return p_gui_id;
    }
    public static List<List<Integer>> S_getNumset(int guiID) {
        return  (List<List<Integer>>) pS_config.getList(Roman.S_serialize(guiID)+".grid");
    }

    public static List<List<Integer>> S_getNumset(String guiRoman) {
        return  (List<List<Integer>>) pS_config.getList(guiRoman +".grid");
    }

    public List<List<Integer>> getNumset() {
        return  S_getNumset(p_roman);
    }
    public static boolean S_getCancelled(int guiID) {
        return pS_config.getBoolean(Roman.S_serialize(guiID)+".cancelled");
    }

    public static boolean S_getCancelled(String guiRoman) {
        return pS_config.getBoolean(guiRoman +".cancelled");
    }

    public boolean getCancelled() {
        return S_getCancelled(p_roman);
    }
    public Component getName() {
        return p_name;
    }
    public Gui setName(Component name) {
        p_name = name;
        return this;
    }
    public Gui setPlayer(Player player) {
        p_player = player;
        return this;
    }
    public Player getPlayer() {
        return p_player;
    }
    public Inventory getInv() {
        return p_inv;
    }
    public Gui setInv(Inventory inv) {
        p_inv = inv;
        return this;
    }
    public Gui gridReader() {
        p_inv = Bukkit.createInventory(null, p_n, Chat.S_componentColored(Chat.S_plainComponent("[GUI]  ").append(p_name)));
        for (int i = 0; i<(p_n/9); i++) {
            final List<Integer> F_tmp = p_num_set.get(i);
            for (int j = 0; j<9; j++){
                ItemStack k = guiPart(F_tmp.get(j));
                p_inv.setItem(Lst.S_toSlot(i, j), k);
            }
        }
        p_inv.setItem(44, pF_item.get());
        return this;
    }
    public static ItemStack S_guiPart(int guiID, Gui guiInstance) {
        final String F_id = Roman.S_serialize(guiID);
        Item item = new Item();
        item.material(Material.matchMaterial(pS_config.getString(F_id + ".material")));
        if (pS_config.getBoolean(F_id+".custom")) {
            if ((guiID > 1 && guiID < 200) || guiID == 999)
                item.customModelData(1);
            else
                item.customModelData(guiID);
        }
        if (pS_config.contains(F_id+".name"))
            item.displayName(pS_config.getString(F_id+".name"));
        if (pS_config.contains(F_id+".lore"))
            item.lore(Lst.S_minimessageColoredList((List<String>) pS_config.getList(F_id + ".lore")));
        try {
            final Class<?> F_gui = Class.forName("net.rainbowcreation.core.gui." + pS_config.getString(F_id + ".name") + "Gui");
            Object[] obj = {guiInstance, item};
            Class<?>[] params = new Class[obj.length];
            params[0] = Gui.class;
            params[1] = Item.class;
            item = (Item) F_gui.getDeclaredMethod("runSelf", params).invoke(F_gui.newInstance(), obj);
        } catch (Exception ignored) {}
        return item.get();
    }

    public ItemStack guiPart(int guiID) {
        return S_guiPart(guiID, pF_instance);
    }

    public Inventory get() {
        if (p_inv == null)
            gridReader();
        try {
            Class<?> gui = Class.forName("net.rainbowcreation.core.gui."+pS_config.getString(p_roman +".name")+"Gui");
            p_inv = (Inventory) gui.getDeclaredMethod("run", Gui.class).invoke(gui.newInstance(), pF_instance);
        } catch (Exception ignored) {}
        return p_inv;
    }
}
