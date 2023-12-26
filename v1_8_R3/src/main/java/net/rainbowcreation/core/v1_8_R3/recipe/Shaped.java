package net.rainbowcreation.core.v1_8_R3.recipe;

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
