package uk.matvey.kit.random

import uk.matvey.kit.string.StringKit.ALL_SYMBOLS
import uk.matvey.kit.string.StringKit.ALPHANUMERIC
import uk.matvey.kit.string.StringKit.DIGITS
import uk.matvey.kit.string.StringKit.LETTERS
import uk.matvey.kit.string.StringKit.capital
import kotlin.random.Random

object RandomKit {

    private val DOMAINS = listOf(
        "au",
        "ca",
        "co",
        "com",
        "de",
        "edu",
        "es",
        "fr",
        "gov",
        "io",
        "it",
        "net",
        "nl",
        "org",
        "ru",
        "uk",
    )

    val RANDOM = Random.Default

    fun randomInt() = RANDOM.nextInt()

    fun randomInt(until: Int) = RANDOM.nextInt(until)

    fun randomInt(range: IntRange) = RANDOM.nextInt(range.first, range.last())

    fun randomLong() = RANDOM.nextLong()

    fun randomLong(until: Long) = RANDOM.nextLong(until)

    fun randomLong(range: LongRange) = RANDOM.nextLong(range.first, range.last())

    fun randomBool() = RANDOM.nextBoolean()

    fun randomStr(
        length: Int = randomInt(32),
        chars: List<Char> = ALL_SYMBOLS,
    ): String {
        require(length >= 0) { "Length must be non-negative" }
        return buildString {
            repeat(length) {
                append(chars[randomInt(chars.size)])
            }
        }
    }

    fun randomAlphabetic(length: Int = randomInt(1..<32)): String {
        return randomStr(length, LETTERS)
    }

    fun randomNumeric(length: Int = randomInt(1..<32)): String {
        return randomStr(length, DIGITS)
    }

    fun randomAlphanumeric(length: Int = randomInt(1..<32)): String {
        return randomStr(length, ALPHANUMERIC)
    }

    fun randomName(): String {
        return capital(randomAlphabetic(randomInt(2..12)).lowercase())
    }

    fun randomFullName(): String {
        return "${randomName()} ${randomName()}"
    }

    fun randomEmail(): String {
        return "${randomName().lowercase()}@${randomAlphanumeric(randomInt(1..12)).lowercase()}.${DOMAINS.random()}"
    }

    fun randomUrl(): String {
        val name = (0..randomInt(1..<3))
            .joinToString(".") {
                randomAlphanumeric(randomInt(1..12))
            }
        return "https://$name.${DOMAINS.random()}"
    }
}