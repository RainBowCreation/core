package net.rainbowcreation.core.recipe;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.utils.Remap;

public class Unshaped implements IRegistry {
    @Override
    public void register() {

        // per version shaped recipe
        final IRegistry unshaped = (IRegistry) Remap.castInterface(IRegistry.class, Core.getInstance().version, "recipe.Unshaped");
        if (unshaped == null)
            return;
        unshaped.register();
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
