package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.random.RandomKit.randomUrl
import java.net.URI

class UriSerializerTest {

    @Test
    fun `should serialize and deserialize URI`() {
        // given
        val uri = URI(randomUrl())

        // when
        val json = jsonSerialize(uri)
        val deserialized = jsonDeserialize<URI>(json)

        // then
        assertThat(deserialized).isEqualTo(uri)
    }
}