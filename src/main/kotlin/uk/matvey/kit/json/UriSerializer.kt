package uk.matvey.kit.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.URI

object UriSerializer : KSerializer<URI> {

    override val descriptor = PrimitiveSerialDescriptor("URI", STRING)

    override fun deserialize(decoder: Decoder): URI {
        return URI(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: URI) {
        return encoder.encodeString(value.toString())
    }
}
