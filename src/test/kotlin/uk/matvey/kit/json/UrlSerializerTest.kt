package uk.matvey.kit.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.json.JsonKit.jsonDeserialize
import uk.matvey.kit.json.JsonKit.jsonSerialize
import uk.matvey.kit.random.RandomKit.randomUrl
import java.net.URI
import java.net.URL

class UrlSerializerTest {

    @Test
    fun `should serialize and deserialize URL`() {
        // given
        val url = URI(randomUrl()).toURL()

        // when
        val json = jsonSerialize(url)
        val deserialized = jsonDeserialize<URL>(json)

        // then
        assertThat(deserialized).isEqualTo(url)
    }
}