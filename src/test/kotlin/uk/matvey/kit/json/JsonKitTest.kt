package uk.matvey.kit.json

import io.kotest.assertions.json.shouldEqualJson
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.JSON
import uk.matvey.kit.json.JsonKit.arr
import uk.matvey.kit.json.JsonKit.arrOrNull
import uk.matvey.kit.json.JsonKit.asBool
import uk.matvey.kit.json.JsonKit.asBoolOrNull
import uk.matvey.kit.json.JsonKit.asLong
import uk.matvey.kit.json.JsonKit.asLongOrNull
import uk.matvey.kit.json.JsonKit.asStr
import uk.matvey.kit.json.JsonKit.asStrOrNull
import uk.matvey.kit.json.JsonKit.bool
import uk.matvey.kit.json.JsonKit.boolOrNull
import uk.matvey.kit.json.JsonKit.jsonArrayEncode
import uk.matvey.kit.json.JsonKit.jsonArrayParse
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonObjectEncode
import uk.matvey.kit.json.JsonKit.jsonObjectParse
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.json.JsonKit.long
import uk.matvey.kit.json.JsonKit.longOrNull
import uk.matvey.kit.json.JsonKit.obj
import uk.matvey.kit.json.JsonKit.objAt
import uk.matvey.kit.json.JsonKit.objAtOrNull
import uk.matvey.kit.json.JsonKit.objOrNull
import uk.matvey.kit.json.JsonKit.str
import uk.matvey.kit.json.JsonKit.strOrNull
import uk.matvey.kit.random.RandomKit.randomBool
import uk.matvey.kit.random.RandomKit.randomHttps
import uk.matvey.kit.random.RandomKit.randomLong
import uk.matvey.kit.random.RandomKit.randomStr
import uk.matvey.kit.time.TimeKit.instant
import uk.matvey.kit.time.TimeKit.localDate
import uk.matvey.kit.time.TimeKit.localDateTime
import uk.matvey.kit.time.TimeKit.year
import uk.matvey.kit.time.TimeKit.yearMonth
import java.net.URI
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.YearMonth
import java.util.UUID
import java.util.UUID.randomUUID

class JsonKitTest {

    @Serializable
    data class Dummy(
        val string: String,
        val long: Long,
        val bool: Boolean,
        val obj: JsonObject,
        val arr: JsonArray,
        val instant: @Contextual Instant,
        val localDate: @Contextual LocalDate,
        val localDateTime: @Contextual LocalDateTime,
        val uri: @Contextual URI,
        val uuid: @Contextual UUID,
        val yearMonth: @Contextual YearMonth,
        val year: @Contextual Year,
    )

    private val dummy = Dummy(
        string = randomStr(),
        long = randomLong(),
        bool = randomBool(),
        obj = buildJsonObject {
            put("key", "value")
        },
        arr = buildJsonArray {
            add("value")
            addJsonObject {
                put("key", "value")
            }
        },
        instant = instant(),
        localDate = localDate(),
        localDateTime = localDateTime(),
        uri = URI.create(randomHttps()),
        uuid = randomUUID(),
        yearMonth = yearMonth(),
        year = year(),
    )

    private val obj = """
        { 
            "string": "${dummy.string}",
            "long": ${dummy.long},
            "bool": ${dummy.bool},
            "instant": "${dummy.instant}",
            "localDate": "${dummy.localDate}",
            "localDateTime": "${dummy.localDateTime}",
            "uri": "${dummy.uri}",
            "uuid": "${dummy.uuid}",
            "yearMonth": "${dummy.yearMonth}",
            "year": "${dummy.year}"
        }
    """.trimIndent()

    @Test
    fun `should serialize and deserialize`() {
        // when / then
        assertThat(jsonDeserialize<Dummy>(jsonSerialize<Dummy>(dummy))).isEqualTo(dummy)
    }

    @Test
    fun `should encode values`() {
        // when / then
        assertThat(JSON.decodeFromJsonElement<Dummy>(jsonObjectEncode(dummy))).isEqualTo(dummy)
        assertThat(JSON.decodeFromJsonElement<List<Dummy>>(jsonArrayEncode(listOf(dummy)))).containsExactly(dummy)
    }

    @Test
    fun `should parse values`() {
        // when / then
        JSON.encodeToString<JsonObject>(jsonObjectParse(obj)) shouldEqualJson obj
        JSON.encodeToString<JsonArray>(jsonArrayParse("[$obj]")) shouldEqualJson "[$obj]"
    }

    @Test
    fun `should access values`() {
        // when
        val json = JSON.encodeToJsonElement(dummy).jsonObject

        // then
        assertThat(json["string"].asStrOrNull()).isEqualTo(dummy.string)
        assertThat(json["wrong"].asStrOrNull()).isNull()
        assertThat(json["string"].asStr()).isEqualTo(dummy.string)

        assertThat(json["long"].asLongOrNull()).isEqualTo(dummy.long)
        assertThat(json["wrong"].asLongOrNull()).isNull()
        assertThat(json["long"].asLong()).isEqualTo(dummy.long)

        assertThat(json["bool"].asBoolOrNull()).isEqualTo(dummy.bool)
        assertThat(json["wrong"].asBoolOrNull()).isNull()
        assertThat(json["bool"].asBool()).isEqualTo(dummy.bool)

        assertThat(json.strOrNull("string")).isEqualTo(dummy.string)
        assertThat(json.strOrNull("wrong")).isNull()
        assertThat(json.str("string")).isEqualTo(dummy.string)

        assertThat(json.longOrNull("long")).isEqualTo(dummy.long)
        assertThat(json.longOrNull("wrong")).isNull()
        assertThat(json.long("long")).isEqualTo(dummy.long)

        assertThat(json.boolOrNull("bool")).isEqualTo(dummy.bool)
        assertThat(json.boolOrNull("wrong")).isNull()
        assertThat(json.bool("bool")).isEqualTo(dummy.bool)

        assertThat(json.objOrNull("obj")).isEqualTo(dummy.obj)
        assertThat(json.objOrNull("wrong")).isNull()
        assertThat(json.obj("obj")).isEqualTo(dummy.obj)

        assertThat(json.arrOrNull("arr")).isEqualTo(dummy.arr)
        assertThat(json.arrOrNull("wrong")).isNull()
        assertThat(json.arr("arr")).isEqualTo(dummy.arr)

        assertThat(json.arr("arr").objAtOrNull(1)).isEqualTo(dummy.arr[1])
        assertThat(json.arr("arr").objAtOrNull(999)).isNull()
        assertThat(json.arr("arr").objAt(1)).isEqualTo(dummy.arr[1])
    }
}
