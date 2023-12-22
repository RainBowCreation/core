package net.rainbowcreation.core.velocity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.TabCompleteEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import net.rainbowcreation.core.velocity.utils.Reference;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class Velocity {

    @Subscribe(order = PostOrder.LATE)
    public void onTabComplete(TabCompleteEvent event) {
        Player player = event.getPlayer();

        // Check if the player is from the backend server
        if (isFromBackendServer(player)) {
            // Modify the suggestions as needed
            event.getSuggestions().clear();
            event.getSuggestions().add("/login");
            event.getSuggestions().add("/register");
            event.getSuggestions().add("/server");
        }
    }

    private boolean isFromBackendServer(Player player) {
        // Implement logic to check if the player is from the backend server
        // You may need to use player.getProtocolVersion() or other Velocity API methods
        return true;
    }
}
