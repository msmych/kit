package uk.matvey.kit.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.JSON
import java.net.URI
import java.time.Instant
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
            val uri: @Contextual URI,
            val uuid: @Contextual UUID,
            val year: @Contextual Year,
            val yearMonth: @Contextual YearMonth,
        )

        val dummy = Dummy(
            instant = Instant.now(),
            uri = URI.create("https://matvey.uk"),
            uuid = randomUUID(),
            year = Year.now(),
            yearMonth = YearMonth.now(),
        )

        // when
        val serialized = JSON.encodeToString(dummy)
        val deserialized = JSON.decodeFromString<Dummy>(serialized)

        // then
        assertThat(deserialized).isEqualTo(dummy)
    }
}