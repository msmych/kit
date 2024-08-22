package uk.matvey.kit.json

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
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

    inline fun <reified T> jsonObjectSerialize(value: T): JsonObject {
        return JSON.encodeToJsonElement(value).jsonObject
    }

    fun jsonObjectDeserialize(json: String): JsonObject {
        return JSON.parseToJsonElement(json).jsonObject
    }

    inline fun <reified T> jsonArraySerialize(values: Collection<T>): JsonArray {
        return JSON.encodeToJsonElement(values).jsonArray
    }

    fun jsonArrayDeserialize(json: String): JsonArray {
        return JSON.parseToJsonElement(json).jsonArray
    }

    fun JsonObject.strOrNull(key: String): String? {
        return get(key)?.jsonPrimitive?.contentOrNull
    }

    fun JsonObject.str(key: String): String {
        return requireNotNull(strOrNull(key))
    }

    fun JsonObject.longOrNull(key: String): Long? {
        return get(key)?.jsonPrimitive?.longOrNull
    }

    fun JsonObject.long(key: String): Long {
        return requireNotNull(longOrNull(key))
    }

    fun JsonObject.boolOrNull(key: String): Boolean? {
        return get(key)?.jsonPrimitive?.booleanOrNull
    }

    fun JsonObject.bool(key: String): Boolean {
        return requireNotNull(boolOrNull(key))
    }

    fun JsonObject.objOrNull(key: String): JsonObject? {
        return get(key)?.jsonObject
    }

    fun JsonObject.obj(key: String): JsonObject {
        return requireNotNull(objOrNull(key))
    }

    fun JsonObject.arrOrNull(key: String): JsonArray? {
        return get(key)?.jsonArray
    }

    fun JsonObject.arr(key: String): JsonArray {
        return requireNotNull(arrOrNull(key))
    }

    fun JsonArray.objAt(index: Int): JsonObject {
        return get(index).jsonObject
    }
}
