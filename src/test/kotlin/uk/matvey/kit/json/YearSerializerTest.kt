package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import java.time.Year

class YearSerializerTest {

    @Test
    fun `should serialize and deserialize Year`() {
        // given
        val year = Year.now()

        // when
        val json = jsonSerialize(year)
        val deserialized = jsonDeserialize<Year>(json)

        // then
        assertThat(deserialized).isEqualTo(year)
    }
}