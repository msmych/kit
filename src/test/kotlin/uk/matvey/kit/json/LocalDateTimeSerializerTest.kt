package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import java.time.LocalDateTime

class LocalDateTimeSerializerTest {

    @Test
    fun `should serialize and deserialize LocalDateTime`() {
        // given
        val localDateTime = LocalDateTime.now()

        // when
        val json = jsonSerialize(localDateTime)
        val deserialized = jsonDeserialize<LocalDateTime>(json)

        // then
        assertThat(deserialized).isEqualTo(localDateTime)
    }
}