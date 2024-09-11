package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import java.time.YearMonth

class YearMonthSerializerTest {

    @Test
    fun `should serialize and deserialize YearMonth`() {
        // given
        val yearMonth = YearMonth.now()

        // when
        val json = jsonSerialize(yearMonth)
        val deserialized = jsonDeserialize<YearMonth>(json)

        // then
        assertThat(deserialized).isEqualTo(yearMonth)
    }
}