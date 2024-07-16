package uk.matvey.kit.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.JSON
import java.net.URI
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
            val uri: @Contextual URI,
            val uuid: @Contextual UUID,
            val yearMonth: @Contextual YearMonth,
            val year: @Contextual Year,
        )

        val dummy = Dummy(
            URI.create("https://matvey.uk"),
            randomUUID(),
            YearMonth.parse("2024-01"),
            Year.of(2024),
        )

        // when
        val serialized = JSON.encodeToString(dummy)
        val deserialized = JSON.decodeFromString<Dummy>(serialized)

        // then
        assertThat(deserialized).isEqualTo(dummy)
    }
}