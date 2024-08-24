package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.time.TimeKit.instant
import java.time.Instant

class InstantSerializerTest {

    @Test
    fun `should serialize and deserialize Instant`() {
        // given
        val instant = instant()

        // when
        val json = jsonSerialize(instant)
        val deserialized = jsonDeserialize<Instant>(json)

        // then
        assertThat(deserialized).isEqualTo(instant)
    }
}