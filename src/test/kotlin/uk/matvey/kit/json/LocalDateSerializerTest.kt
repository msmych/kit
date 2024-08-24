package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.time.TimeKit.localDate
import java.time.LocalDate

class LocalDateSerializerTest {

    @Test
    fun `should serialize and deserialize LocalDate`() {
        // given
        val localDate = localDate()

        // when
        val json = jsonSerialize(localDate)
        val deserialized = jsonDeserialize<LocalDate>(json)

        // then
        assertThat(deserialized).isEqualTo(localDate)
    }
}