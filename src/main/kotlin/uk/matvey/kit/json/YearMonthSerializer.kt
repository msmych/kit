package uk.matvey.kit.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.YearMonth

object YearMonthSerializer : KSerializer<YearMonth> {

    override val descriptor = PrimitiveSerialDescriptor("YearMonth", STRING)

    override fun deserialize(decoder: Decoder): YearMonth {
        return YearMonth.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: YearMonth) {
        return encoder.encodeString(value.toString())
    }
}
