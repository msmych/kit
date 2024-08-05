package uk.matvey.kit.string

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.string.StringKit.capital

class StringKitTest {

    @Test
    fun `should capitalize string`() {
        // given
        val str = "hello"

        // when
        val capitalized = capital(str)

        // then
        assertThat(capitalized).isEqualTo("Hello")
    }
}