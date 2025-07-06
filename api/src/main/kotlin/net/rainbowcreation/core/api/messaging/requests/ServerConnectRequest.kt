package net.rainbowcreation.core.api.messaging.requests

import java.io.Serializable

/**
 * A data class representing a request to connect a player to a specific server.
 *
 * It implements [Serializable] so it can be converted into a byte stream
 * for sending through a plugin messaging channel.
 *
 * @property playerName The name of the player to be moved.
 * @property targetServer The name of the server to connect the player to.
 */
data class ServerConnectRequest(val playerName: String, val targetServer: String) : Serializable