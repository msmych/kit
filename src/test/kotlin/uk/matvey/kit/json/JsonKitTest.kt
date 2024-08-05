package uk.matvey.kit.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.random.RandomKit.randomHttps
import java.net.URI
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.YearMonth
import java.util.UUID
import java.util.UUID.randomUUID

class JsonKitTest {

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
            val year: @Contextual Year,
            val yearMonth: @Contextual YearMonth,
        )

        val dummy = Dummy(
            instant = Instant.now(),
            localDate = LocalDate.now(),
            localDateTime = LocalDateTime.now(),
            uri = URI.create(randomHttps()),
            uuid = randomUUID(),
            year = Year.now(),
            yearMonth = YearMonth.now(),
        )

        // when
        val serialized = jsonSerialize(dummy)
        val deserialized = jsonDeserialize<Dummy>(serialized)

        // then
        assertThat(deserialized).isEqualTo(dummy)
    }
}