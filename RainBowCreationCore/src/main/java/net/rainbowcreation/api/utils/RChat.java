package net.rainbowcreation.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chattype conversion module
 */
public final class RChat {
    private RChat() {
        // void
    }

    /**
     *
     * @param minimessage
     * @return Component
     */
    public static @NotNull Component minimessageComponent(String minimessage) {
        return MiniMessage.miniMessage().deserialize(minimessage);
    }

    /**
     *
     * @param minimessage
     * @return LegacyString
     */
    public static @NotNull String minimessageLegacy(String minimessage) {
        return componentLegacy(minimessageComponent(minimessage));
    }

    /**
     *
     * @param minimessage
     * @return PlaintextString
     */
    public static @NotNull String minimessagePlain(String minimessage) {
        return componentPlain(minimessageComponent(minimessage));
    }

    /**
     *
     * @param legacy
     * @return Component
     */
    public static @NotNull Component legacyComponent(String legacy) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(legacy);
    }

    /**
     *
     * @param legacy
     * @return MinimessageString
     */
    public static @NotNull String legacyMinimessage(String legacy) {
        return componentMinimessage(legacyComponent(legacy));
    }

    /**
     *
     * @param legacy
     * @return PaintextString
     */
    public static @NotNull String legacyPlain(String legacy) {
        return componentPlain(legacyComponent(legacy));
    }

    /**
     *
     * @param component
     * @return PlaintextString
     */
    public static @NotNull String componentPlain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    /**
     *
     * @param component
     * @return MinimessageString
     */
    public static @NotNull String componentMinimessage(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    /**
     *
     * @param component
     * @return LegacyString
     */
    public static @NotNull String componentLegacy(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    /**
     *
     * @param plain
     * @return Component
     */
    public static @NotNull Component plainComponent(String plain) {
        return PlainTextComponentSerializer.plainText().deserialize(plain);
    }

    /**
     *
     * @param plain
     * @return LegacyString
     */
    public static @NotNull String plainLegacy(String plain) {
        return componentLegacy(plainComponent(plain));
    }

    /**
     *
     * @param plain
     * @return MinimessageString
     */
    public static @NotNull String plainMinimessage(String plain) {
        return componentMinimessage(plainComponent(plain));
    }

    /**
     *
     * @param legacy
     * @return Colored ChatColor
     */
    @Contract("_ -> new")
    public static @NotNull String legacyColored(String legacy) {
        if (legacy.contains("§"))
            return ChatColor.translateAlternateColorCodes('§', legacy);
        return ChatColor.translateAlternateColorCodes('&', legacy);
    }

    /**
     *
     * @param component
     * @return Colored Chatcolor
     */
    @Contract("_ -> new")
    public static @NotNull String componentColored(Component component) {
        return legacyColored(componentLegacy(component));
    }

    /**
     *
     * @param minimessage
     * @return Colored Charcolor
     */
    @Contract("_ -> new")
    public static @NotNull String minimessageColored(String minimessage) {
        return legacyColored(minimessageLegacy(minimessage));
    }

    /**
     *
     * @param player
     * @param minimessage
     */
    public static void sendPlayerMessage(@NotNull Player player, String minimessage) {
        player.sendMessage(legacyColored(legacyMinimessage(minimessage)));
    }
}
