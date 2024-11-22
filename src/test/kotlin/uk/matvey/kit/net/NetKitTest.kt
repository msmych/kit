package uk.matvey.kit.net

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import uk.matvey.kit.net.NetKit.queryParam
import uk.matvey.kit.net.NetKit.queryParamOrNull
import uk.matvey.kit.net.NetKit.queryParams
import java.net.URI

class NetKitTest {

    @Test
    fun `should extract query params`() {
        // when / then
        assertThat(URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParams("a"))
            .containsExactly("1", "2", "3")
        assertThat(URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParams("no")).isEmpty()
        assertThat(URI.create("https://example.com").queryParams("no")).isEmpty()

        assertThat(URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParamOrNull("a")).isEqualTo("1")
        assertThat(URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParamOrNull("no")).isNull()
        assertThat(URI.create("https://example.com").queryParamOrNull("no")).isNull()

        assertThat(URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParam("a")).isEqualTo("1")
        assertThatThrownBy { URI.create("https://example.com?a=1&a=2&b=9&a=3&c=0").queryParam("no") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Query parameter 'no' is missing in https://example.com?a=1&a=2&b=9&a=3&c=0")
        assertThatThrownBy { URI.create("https://example.com").queryParam("no") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Query parameter 'no' is missing in https://example.com")
    }
}