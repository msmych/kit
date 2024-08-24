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
            contextual(UuidSerializer)
            contextual(YearMonthSerializer)
            contextual(YearSerializer)
        }
    }

    fun json() = JSON

    inline fun <reified T> jsonSerialize(value: T): String {
        return json().encodeToString(value)
    }

    inline fun <reified T> jsonDeserialize(json: String): T {
        return json().decodeFromString(json)
    }

    inline fun <reified T> jsonObjectSerialize(value: T): JsonObject {
        return json().encodeToJsonElement(value).jsonObject
    }

    fun jsonObjectDeserialize(json: String): JsonObject {
        return json().parseToJsonElement(json).jsonObject
    }

    inline fun <reified T> jsonArraySerialize(values: Collection<T>): JsonArray {
        return json().encodeToJsonElement(values).jsonArray
    }

    fun jsonArrayDeserialize(json: String): JsonArray {
        return json().parseToJsonElement(json).jsonArray
    }

    fun JsonElement.asStrOrNull() = jsonPrimitive.contentOrNull

    fun JsonElement.asStr() = jsonPrimitive.content

    fun JsonElement.asLongOrNull() = jsonPrimitive.longOrNull

    fun JsonElement.asLong() = jsonPrimitive.long

    fun JsonElement.asBoolOrNull() = jsonPrimitive.booleanOrNull

    fun JsonElement.asBool() = jsonPrimitive.boolean

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

    fun JsonArray.objAt(index: Int) = get(index).jsonObject
}
