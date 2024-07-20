package uk.matvey.kit.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object JsonKit {

    val JSON = Json {
        serializersModule = SerializersModule {
            contextual(InstantSerializer)
            contextual(LocalDateSerializer)
            contextual(LocalDateTimeSerializer)
            contextual(UriSerializer)
            contextual(UuidSerializer)
            contextual(YearMonthSerializer)
            contextual(YearSerializer)
        }
    }

    inline fun <reified T> jsonSerialize(value: T): String {
        return JSON.encodeToString(value)
    }

    inline fun <reified T> jsonDeserialize(json: String): T {
        return JSON.decodeFromString(json)
    }
}