package net.rainbowcreation.multiVersionsPluginExample.core.common.messaging

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * A utility object for serializing message objects into byte arrays.
 */
object MessageSerializer {
    /**
     * Converts a [Serializable] object into a [ByteArray].
     *
     * @param data The object to serialize. It must implement the Serializable interface.
     * @return The object represented as a byte array.
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Throws(IOException::class)
    fun serialize(data: Serializable): ByteArray {
        // Creates a stream to write byte data to memory.
        val byteStream = ByteArrayOutputStream()

        // Wraps the byte stream to write objects to it.
        // The 'use' block ensures the streams are automatically closed.
        ObjectOutputStream(byteStream).use {
            it.writeObject(data)
        }

        return byteStream.toByteArray()
    }
}
