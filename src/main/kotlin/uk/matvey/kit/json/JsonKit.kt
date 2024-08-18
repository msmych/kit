package uk.matvey.kit.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object JsonKit {

    val JSON = Json {
        ignoreUnknownKeys = true
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

    fun jsonObjectDeserialize(json: String): JsonObject {
        return JSON.parseToJsonElement(json).jsonObject
    }

    fun jsonArrayDeserialize(json: String): JsonArray {
        return JSON.parseToJsonElement(json).jsonArray
    }

    fun JsonObject.str(key: String): String {
        return getValue(key).jsonPrimitive.content
    }

    fun JsonObject.long(key: String): Long {
        return getValue(key).jsonPrimitive.long
    }

    fun JsonObject.bool(key: String): Boolean {
        return getValue(key).jsonPrimitive.boolean
    }

    fun JsonObject.obj(key: String): JsonObject {
        return getValue(key).jsonObject
    }

    fun JsonObject.arr(key: String): JsonArray {
        return getValue(key).jsonArray
    }

    fun JsonArray.objAt(index: Int): JsonObject {
        return get(index).jsonObject
    }
}