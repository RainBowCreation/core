package net.rainbowcreation.core.proxies.commands

import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import net.rainbowcreation.core.proxies.ProxyDetector

class ProxyCommand(private val proxy: Any) : Command("proxybroadcast"), SimpleCommand {

    /**
     * BungeeCord's command execution method.
     */
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (ProxyDetector.detectProxy() != ProxyDetector.ProxyServerType.BUNGEECORD) return

        val message = args.joinToString(" ")
        if (message.isBlank()) {
            sender.sendMessage(TextComponent("§cUsage: /proxybroadcast <message>"))
            return
        }

        (proxy as ProxyServer).broadcast(TextComponent("§b[Proxy] §e$message"))
    }

    /**
     * Velocity's command execution method.
     */
    override fun execute(invocation: SimpleCommand.Invocation) {
        if (ProxyDetector.detectProxy() != ProxyDetector.ProxyServerType.VELOCITY) return

        val message = invocation.arguments().joinToString(" ")
        if (message.isBlank()) {
            invocation.source().sendMessage(Component.text("§cUsage: /proxybroadcast <message>"))
            return
        }

        // Get all players and send the message to each one.
        val messageComponent = Component.text("§b[Proxy] §e$message")
        (proxy as com.velocitypowered.api.proxy.ProxyServer).allPlayers.forEach { player ->
            player.sendMessage(messageComponent)
        }
    }
}