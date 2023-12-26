package net.rainbowcreation.core.command;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IEvent;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.utils.Remap;

public class Command implements IRegistry {
    @Override
    public void register() {
        // global command
        Core.getInstance().getCommand("rbc").setExecutor(new Rbc());


        // other version register
        final IRegistry command = (IRegistry) Remap.castInterface(IRegistry.class, Core.getInstance().version, "command.Command");
        if (command == null)
            return;
        command.register();
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
