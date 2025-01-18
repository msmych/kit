package uk.matvey.kit.string

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.string.StringKit.capitalize
import uk.matvey.kit.string.StringKit.fullName
import uk.matvey.kit.string.StringKit.toInstant
import uk.matvey.kit.string.StringKit.toLocalDate
import uk.matvey.kit.string.StringKit.toLocalDateTime
import uk.matvey.kit.string.StringKit.toUuid
import uk.matvey.kit.string.StringKit.toYearMonth
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.UUID.randomUUID

class StringKitTest {

    @Test
    fun `should capitalize string`() {
        // given
        val str = "hello"

        // when
        val capitalized = capitalize(str)

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

    @Test
    fun `should convert string to LocalDateTime`() {
        // given
        val dateTime = LocalDateTime.now()

        // when / then
        assertThat(dateTime.toString().toLocalDateTime()).isEqualTo(dateTime)
    }

    @Test
    fun `should convert string to YearMonth`() {
        // given
        val yearMonth = YearMonth.now()

        // when / then
        assertThat(yearMonth.toString().toYearMonth()).isEqualTo(yearMonth)
    }

    @Test
    fun `should convert string to Instant`() {
        // given
        val instant = Instant.now()

        // when / then
        assertThat(instant.toString().toInstant()).isEqualTo(instant)
    }

    @Test
    fun `should join first and last names`() {
        // given
        val firstName = "John"
        val lastName = "Doe"

        // when / then
        assertThat(fullName(firstName, lastName)).isEqualTo("John Doe")
        assertThat(fullName(firstName, null)).isEqualTo("John")
        assertThat(fullName(null, lastName)).isEqualTo("Doe")
        assertThat(fullName(null, null)).isNull()
    }
}