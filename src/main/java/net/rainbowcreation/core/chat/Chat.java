package net.rainbowcreation.core.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Chat {
    public static Component minimessageComponent(String minimessage) {
        return MiniMessage.miniMessage().deserialize(minimessage);
    }
    public static String minimessageLegacy(String minimessage) {
        return componentLegacy(minimessageComponent(minimessage));
    }
    public static String minimessagePlain(String minimessage) {
        return componentPlain(minimessageComponent(minimessage));
    }
    public static Component legacyComponent(String legacy) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(legacy);
    }
    public static String legacyMinimessage(String legacy) {
        return componentMinimessage(legacyComponent(legacy));
    }
    public static String legacyPlain(String legacy) {
        return componentPlain(legacyComponent(legacy));
    }

    public static String componentPlain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
    public static @NotNull String componentMinimessage(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
    public static String componentLegacy(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
    public static Component plainComponent(String plain) {
        return PlainTextComponentSerializer.plainText().deserialize(plain);
    }
    public static String plainLegacy(String plain) {
        return componentLegacy(plainComponent(plain));
    }
    public static String plainMinimessage(String plain) {
        return componentMinimessage(plainComponent(plain));
    }
    public static String legacyColored(String legacy) {
        return ChatColor.translateAlternateColorCodes('&', legacy);
    }
    public static String componentColored(Component component) {
        return legacyColored(componentLegacy(component));
    }
    public static String minimessageColored(String minimessage) {
        return legacyColored(minimessageLegacy(minimessage));
    }
    public static void sendPlayerMessage(Player player, String minimessage) {
        player.sendMessage(legacyColored(legacyMinimessage(minimessage)));
    }
}
