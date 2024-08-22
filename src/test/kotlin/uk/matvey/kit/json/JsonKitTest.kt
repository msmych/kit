package uk.matvey.kit.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.arr
import uk.matvey.kit.json.JsonKit.bool
import uk.matvey.kit.json.JsonKit.jsonArrayDeserialize
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonObjectDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.json.JsonKit.long
import uk.matvey.kit.json.JsonKit.objAt
import uk.matvey.kit.json.JsonKit.str
import uk.matvey.kit.random.RandomKit.randomHttps
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

    private val jsonObject = """
            {
                "key": "value",
                "num": 999,
                "victory": true,
                "obj": {
                    "key": "value"
                },
                "arr": [
                    {
                        "key": "value1"
                    },
                    {
                        "key": "value2"
                    }
                ]
            }
        """.trimIndent()
    private val jsonArray = """
            [
                {
                    "key": "value1"
                },
                {
                    "key": "value2"
                }
            ]
        """.trimIndent()

    @Test
    fun `should serialize and deserialize`() {
        // given
        @Serializable
        data class Dummy(
            val instant: @Contextual Instant,
            val localDate: @Contextual LocalDate,
            val localDateTime: @Contextual LocalDateTime,
            val uri: @Contextual URI,
            val uuid: @Contextual UUID,
            val yearMonth: @Contextual YearMonth,
            val year: @Contextual Year,
        )

        val dummy = Dummy(
            instant = instant(),
            localDate = localDate(),
            localDateTime = localDateTime(),
            uri = URI.create(randomHttps()),
            uuid = randomUUID(),
            yearMonth = yearMonth(),
            year = year(),
        )

        // when
        val serialized = jsonSerialize(dummy)
        val deserialized = jsonDeserialize<Dummy>(serialized)

        // then
        assertThat(deserialized).isEqualTo(dummy)
    }

    @Test
    fun `should deserialize json object and array`() {
        // when / then
        assertThat(jsonObjectDeserialize(jsonObject).str("key")).isEqualTo("value")
        assertThat(jsonArrayDeserialize(jsonArray).objAt(0).str("key")).isEqualTo("value1")
        assertThat(jsonArrayDeserialize(jsonArray).objAt(1).str("key")).isEqualTo("value2")
    }

    @Test
    fun `should access json elements`() {
        // when / then
        val obj = jsonObjectDeserialize(jsonObject)
        val arr = jsonObjectDeserialize(jsonObject).arr("arr")
        assertThat(obj.str("key")).isEqualTo("value")
        assertThat(obj.long("num")).isEqualTo(999L)
        assertThat(obj.bool("victory")).isTrue()
        assertThat(obj.str("key")).isEqualTo("value")
        assertThat(arr.objAt(0).str("key")).isEqualTo("value1")
    }
}
