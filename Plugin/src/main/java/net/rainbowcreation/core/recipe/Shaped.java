package net.rainbowcreation.core.recipe;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.utils.Remap;

public class Shaped implements IRegistry {
    @Override
    public void register() {

        // per version shaped recipe
        final IRegistry shaped = (IRegistry) Remap.castInterface(IRegistry.class, Core.getInstance().version, "recipe.Shaped");
        if (shaped == null)
            return;
        shaped.register();
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
