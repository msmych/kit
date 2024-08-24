package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.time.TimeKit.localTime
import java.time.LocalTime

class LocalTimeSerializerTest {

    @Test
    fun `should serialize and deserialize LocalTime`() {
        // given
        val localTime = localTime()

        // when
        val json = jsonSerialize(localTime)
        val deserialized = jsonDeserialize<LocalTime>(json)

        // then
        assertThat(deserialized).isEqualTo(localTime)
    }
}