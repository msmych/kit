package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import java.math.BigDecimal
import kotlin.random.Random

class BigDecimalSerializerTest {

    @Test
    fun `should serialize and deserialize BigDecimal`() {
        // given
        val bigDecimal = BigDecimal(Random.nextDouble())

        // when
        val json = jsonSerialize(bigDecimal)
        val deserialized = jsonDeserialize<BigDecimal>(json)

        // then
        assertThat(deserialized).isEqualTo(bigDecimal)
    }
}