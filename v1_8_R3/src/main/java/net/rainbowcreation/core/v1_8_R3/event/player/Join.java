package net.rainbowcreation.core.v1_8_R3.event.player;

import net.rainbowcreation.core.api.utils.Chat;
import net.rainbowcreation.core.v1_8_R3.utils.Item;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendTitle(Chat.minimessageColored("Happy New Year!!! <green>2<aqua>0<light_purple>2<red>4"), Chat.minimessageColored("welcome to RainBowCreation"));
        spawnFirework(player.getLocation());
        player.getInventory().addItem(new Item().getrandomFirework());
    }

    public void spawnFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .flicker(true)
                .trail(true)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.PURPLE)
                .withFade(Color.RED)
                .build();

        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);

        firework.setFireworkMeta(fireworkMeta);
    }
}
