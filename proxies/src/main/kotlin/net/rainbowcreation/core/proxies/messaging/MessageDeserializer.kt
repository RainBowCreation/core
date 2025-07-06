package net.rainbowcreation.core.proxies.messaging

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.ObjectInputStream

/**
 * A utility object for deserializing byte arrays back into message objects.
 */
object MessageDeserializer {

    /**
     * Converts a [ByteArray] back into its original [Any] object form.
     *
     * @param data The byte array received from the plugin message channel.
     * @return The deserialized object.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    fun deserialize(data: ByteArray): Any {
        // Creates a stream to read byte data from memory.
        val byteStream = ByteArrayInputStream(data)

        // Wraps the byte stream to read objects from it.
        // The 'use' block ensures the streams are automatically closed.
        return ObjectInputStream(byteStream).use {
            it.readObject()
        }
    }
}