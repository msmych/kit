package uk.matvey.kit.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object JsonKit {

    val JSON = Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(InstantSerializer)
            contextual(LocalDateSerializer)
            contextual(LocalTimeSerializer)
            contextual(LocalDateTimeSerializer)
            contextual(UriSerializer)
            contextual(UrlSerializer)
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

    inline fun <reified T> jsonObjectEncode(value: T): JsonObject {
        return JSON.encodeToJsonElement(value).jsonObject
    }

    fun jsonObjectParse(json: String): JsonObject {
        return JSON.parseToJsonElement(json).jsonObject
    }

    inline fun <reified T> jsonArrayEncode(values: Collection<T>): JsonArray {
        return JSON.encodeToJsonElement(values).jsonArray
    }

    fun jsonArrayParse(json: String): JsonArray {
        return JSON.parseToJsonElement(json).jsonArray
    }

    fun JsonElement?.asStrOrNull() = this?.jsonPrimitive?.contentOrNull

    fun JsonElement?.asStr() = requireNotNull(this).jsonPrimitive.content

    fun JsonElement?.asLongOrNull() = this?.jsonPrimitive?.longOrNull

    fun JsonElement?.asLong() = requireNotNull(this).jsonPrimitive.long

    fun JsonElement?.asBoolOrNull() = this?.jsonPrimitive?.booleanOrNull

    fun JsonElement?.asBool() = requireNotNull(this).jsonPrimitive.boolean

    fun JsonObject.strOrNull(key: String) = get(key)?.asStrOrNull()

    fun JsonObject.str(key: String) = getValue(key).asStr()

    fun JsonObject.longOrNull(key: String) = get(key)?.asLongOrNull()

    fun JsonObject.long(key: String) = getValue(key).asLong()

    fun JsonObject.boolOrNull(key: String) = get(key)?.asBoolOrNull()

    fun JsonObject.bool(key: String) = getValue(key).asBool()

    fun JsonObject.objOrNull(key: String) = get(key)?.jsonObject

    fun JsonObject.obj(key: String) = getValue(key).jsonObject

    fun JsonObject.arrOrNull(key: String) = get(key)?.jsonArray

    fun JsonObject.arr(key: String) = getValue(key).jsonArray

    fun JsonArray.objAtOrNull(index: Int) = getOrNull(index)?.jsonObject

    fun JsonArray.objAt(index: Int) = get(index).jsonObject
}
