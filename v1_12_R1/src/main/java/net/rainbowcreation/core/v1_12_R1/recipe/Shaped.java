package net.rainbowcreation.core.v1_12_R1.recipe;

import net.rainbowcreation.core.api.IRegistry;
import org.bukkit.Bukkit;

public class Shaped implements IRegistry {
    @Override
    public void register() {
        Bukkit.addRecipe(new SWarpScroll().recipe);
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
