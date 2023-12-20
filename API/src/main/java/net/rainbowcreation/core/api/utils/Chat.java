package net.rainbowcreation.core.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Chat {
    public static Component S_minimessageComponent(String minimessage) {
        return MiniMessage.miniMessage().deserialize(minimessage);
    }
    public static String S_minimessageLegacy(String minimessage) {
        return S_componentLegacy(S_minimessageComponent(minimessage));
    }
    public static String S_minimessagePlain(String minimessage) {
        return S_componentPlain(S_minimessageComponent(minimessage));
    }
    public static Component S_legacyComponent(String legacy) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(legacy);
    }
    public static String S_legacyMinimessage(String legacy) {
        return S_componentMinimessage(S_legacyComponent(legacy));
    }
    public static String S_legacyPlain(String legacy) {
        return S_componentPlain(S_legacyComponent(legacy));
    }

    public static String S_componentPlain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
    public static @NotNull String S_componentMinimessage(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
    public static String S_componentLegacy(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
    public static Component S_plainComponent(String plain) {
        return PlainTextComponentSerializer.plainText().deserialize(plain);
    }
    public static String S_plainLegacy(String plain) {
        return S_componentLegacy(S_plainComponent(plain));
    }
    public static String S_plainMinimessage(String plain) {
        return S_componentMinimessage(S_plainComponent(plain));
    }
    public static String S_legacyColored(String legacy) {
        return ChatColor.translateAlternateColorCodes('&', legacy);
    }
    public static String S_componentColored(Component component) {
        return S_legacyColored(S_componentLegacy(component));
    }
    public static String S_minimessageColored(String minimessage) {
        return S_legacyColored(S_minimessageLegacy(minimessage));
    }
    public static void S_sendPlayerMessage(Player player, String minimessage) {
        player.sendMessage(S_legacyColored(S_legacyMinimessage(minimessage)));
    }
}
