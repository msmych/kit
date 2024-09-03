package uk.matvey.kit.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.json
import uk.matvey.kit.json.JsonKit.jsonArrayEncode
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonObjectEncode
import uk.matvey.kit.json.JsonKit.jsonSerialize
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

    private val dummy = Dummy(
        instant = instant(),
        localDate = localDate(),
        localDateTime = localDateTime(),
        uri = URI.create(randomHttps()),
        uuid = randomUUID(),
        yearMonth = yearMonth(),
        year = year(),
    )

    @Test
    fun `should serialize and deserialize`() {
        // when / then
        assertThat(jsonDeserialize<Dummy>(jsonSerialize<Dummy>(dummy))).isEqualTo(dummy)
    }

    @Test
    fun `should encode values`() {
        // when / then
        assertThat(json().decodeFromJsonElement<Dummy>(jsonObjectEncode(dummy))).isEqualTo(dummy)
        assertThat(json().decodeFromJsonElement<List<Dummy>>(jsonArrayEncode(listOf(dummy)))).containsExactly(dummy)
    }
}
