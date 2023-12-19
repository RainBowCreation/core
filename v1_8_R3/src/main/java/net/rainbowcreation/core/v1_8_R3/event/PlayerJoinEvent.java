package net.rainbowcreation.core.v1_8_R3.event;

import net.rainbowcreation.core.api.event.IPlayerJoinEvent;

public class PlayerJoinEvent implements IPlayerJoinEvent {
    @Override
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hi");
    }

    @Override
    public IPlayerJoinEvent newInstance() {
        return this;
    }
}
