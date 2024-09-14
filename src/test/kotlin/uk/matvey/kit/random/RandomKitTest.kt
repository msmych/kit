package uk.matvey.kit.random

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import uk.matvey.kit.random.RandomKit.randomAlphanumeric
import uk.matvey.kit.random.RandomKit.randomBool
import uk.matvey.kit.random.RandomKit.randomInt
import uk.matvey.kit.random.RandomKit.randomLong
import uk.matvey.kit.random.RandomKit.randomStr
import uk.matvey.kit.random.RandomKit.randomUrl
import java.net.URI

class RandomKitTest {

    @Test
    fun `should generate random int`() {
        // when
        val results = (0..<1000).map { randomInt(10) }.groupingBy { it }.eachCount()

        // then
        assertThat(results).hasSize(10)
        assertThat(results.values.max() - results.values.min()).isLessThan(100)
    }

    @Test
    fun `should generate random long`() {
        // when
        val results = (0..<1000).map { randomLong(10) }.groupingBy { it }.eachCount()

        // then
        assertThat(results).hasSize(10)
        assertThat(results.values.max() - results.values.min()).isLessThan(100)
    }

    @Test
    fun `should generate random bool`() {
        // when
        val results = (0..<1000).map { randomBool() }.groupingBy { it }.eachCount()

        // then
        assertThat(results).hasSize(2)
        assertThat(results.values.max() - results.values.min()).isLessThan(100)
    }

    @Test
    fun `should generate random str`() {
        // when
        val randomStr = randomAlphanumeric(10)

        // then
        assertThat(randomStr).hasSize(10)
    }

    @Test
    fun `should generate random str with given chars`() {
        // when
        val randomStr = randomStr(chars = ('2'..'8').toList())

        // then
        assertThat(randomStr).containsOnlyDigits()
            .doesNotContain("0", "1", "9")
    }

    @Test
    fun `should validate random str length`() {
        // when / then
        assertThrows<IllegalArgumentException> { randomAlphanumeric(-1) }
    }

    @Test
    fun `should create random url`() {
        // when
        val randomHttps = randomUrl()

        // then
        assertThat(URI.create(randomHttps).scheme).isEqualTo("https")
    }
}