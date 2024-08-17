package uk.matvey.kit.string

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.string.StringKit.capital
import uk.matvey.kit.string.StringKit.toLocalDate
import uk.matvey.kit.string.StringKit.toUuid
import java.time.LocalDate
import java.util.UUID.randomUUID

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

    @Test
    fun `should convert string to UUID`() {
        // given
        val uuid = randomUUID()

        // when / then
        assertThat(uuid.toString().toUuid()).isEqualTo(uuid)
    }

    @Test
    fun `should convert string to LocalDate`() {
        // given
        val date = LocalDate.now()

        // when / then
        assertThat(date.toString().toLocalDate()).isEqualTo(date)
    }
}