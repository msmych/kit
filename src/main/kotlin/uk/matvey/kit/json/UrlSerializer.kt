package uk.matvey.kit.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.URI
import java.net.URL

object UrlSerializer : KSerializer<URL> {

    override val descriptor = PrimitiveSerialDescriptor("URL", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): URL {
        return URI(decoder.decodeString()).toURL()
    }

    override fun serialize(encoder: Encoder, value: URL) {
        return encoder.encodeString(value.toString())
    }
}