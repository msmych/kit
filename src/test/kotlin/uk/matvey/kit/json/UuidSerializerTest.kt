package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import java.util.UUID
import java.util.UUID.randomUUID

class UuidSerializerTest {

    @Test
    fun `should serialize and deserialize UUID`() {
        // given
        val uuid = randomUUID()

        // when
        val json = jsonSerialize(uuid)
        val deserialized = jsonDeserialize<UUID>(json)

        // then
        assertThat(deserialized).isEqualTo(uuid)
    }
}